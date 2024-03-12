package com.lhr.water.ui.formContent

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.iterator
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.Form.Companion.formFromJson
import com.lhr.water.data.Form.Companion.toJsonString
import com.lhr.water.data.ItemDetail
import com.lhr.water.data.deliveryFieldMap
import com.lhr.water.data.deliveryItemFieldMap
import com.lhr.water.data.form.getEnglishFieldName
import com.lhr.water.data.pickingFieldMap
import com.lhr.water.data.pickingItemFieldMap
import com.lhr.water.data.returningFieldMap
import com.lhr.water.data.returningItemFieldMap
import com.lhr.water.data.transferFieldMap
import com.lhr.water.data.transferItemFieldMap
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.ActivityFormContentBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.dialog.GoodsDialog
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.TransferStatus.transferInput
import com.lhr.water.util.TransferStatus.transferOutput
import com.lhr.water.util.interfaces.FormContentData
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.setPropertyValue
import com.lhr.water.util.showToast
import com.lhr.water.util.transferStatus
import com.lhr.water.util.widget.FormContentDataSpinnerWidget
import com.lhr.water.util.widget.FormGoodsAdd
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormContentDataWidget
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.time.LocalDate

class FormContentActivity : BaseActivity(), View.OnClickListener, FormGoodsAdd.Listener,
    FormContentDataWidget.Listener, FormGoodsDataWidget.Listener, GoodsDialog.Listener {
    private val viewModel: FormContentViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityFormContentBinding? = null
    private val binding get() = _binding!!
    private lateinit var reportTitle: String
    private var jsonString: String? = null
    private var formFieldNameMap: MutableMap<String, String> = linkedMapOf()
    private var formFieldNameList = ArrayList<String>() //表單欄位
    private var formFieldNameEngList = ArrayList<String>() //表單欄位英文名
    private var formFieldContentList = ArrayList<String>() //表單欄位內容

    private var formItemFieldNameMap: MutableMap<String, String> = linkedMapOf()
    private var formItemFieldNameList = ArrayList<String>() //貨物欄位
    private var formItemFieldNameEngList = ArrayList<String>() //貨物欄位英文名
    private var formItemFieldContentList: JSONArray? = null //貨物欄位內容
    private lateinit var jsonObject: JSONObject
    private lateinit var form: Form
    private var isInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 檢查版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            reportTitle = intent.getParcelableExtra("reportTitle", String::class.java) as String
        } else {
            reportTitle = intent.getSerializableExtra("reportTitle") as String
        }
        if (intent.hasExtra("jsonString")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                jsonString = intent.getParcelableExtra("jsonString", String::class.java)
            } else {
                jsonString = intent.getStringExtra("jsonString")
            }
        }
        form = formFromJson(jsonString!!)
//        isInput = isInput(JSONObject(jsonString))

//        bindViewModel()
        initView()
    }

    private fun bindViewModel() {

    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = reportTitle
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        when (reportTitle) {
            getString(R.string.delivery_form) -> {
                formFieldNameList = resources.getStringArray(R.array.delivery_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.delivery_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.delivery_Item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.delivery_item_field_name_eng)
                        .toList() as ArrayList<String>

                formFieldNameMap = deliveryFieldMap.toMutableMap()
                formItemFieldNameMap = deliveryItemFieldMap.toMutableMap()
            }
            getString(R.string.picking_form) -> {
                formFieldNameList = resources.getStringArray(R.array.picking_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.picking_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.picking_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.picking_item_field_name_eng)
                        .toList() as ArrayList<String>

                formFieldNameMap = pickingFieldMap.toMutableMap()
                formItemFieldNameMap = pickingItemFieldMap.toMutableMap()
            }
            getString(R.string.transfer_form) -> {
                formFieldNameList = resources.getStringArray(R.array.transfer_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.transfer_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.transfer_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.transfer_item_field_name_eng)
                        .toList() as ArrayList<String>

                formFieldNameMap = transferFieldMap.toMutableMap()
                formItemFieldNameMap = transferItemFieldMap.toMutableMap()
            }

            getString(R.string.returning_form) -> {
                formFieldNameList = resources.getStringArray(R.array.returning_form_field_name)
                    .toList() as ArrayList<String>
                formFieldNameEngList =
                    resources.getStringArray(R.array.returning_form_field_name_eng)
                        .toList() as ArrayList<String>
                formItemFieldNameList = resources.getStringArray(R.array.returning_item_field_name)
                    .toList() as ArrayList<String>
                formItemFieldNameEngList =
                    resources.getStringArray(R.array.returning_item_field_name_eng)
                        .toList() as ArrayList<String>

                formFieldNameMap = returningFieldMap.toMutableMap()
                formItemFieldNameMap = returningItemFieldMap.toMutableMap()
            }
        }
        // 如果是開啟已有紀錄
//        jsonString?.let {
//            // 將jsonString轉成jsonObject
//            jsonObject = JSONObject(jsonString)
//            // 使用 map 函數根據key列表提取值並創建新的列表
//            formFieldContentList = formFieldNameEngList.map { key ->
//                jsonObject.getString(key)
//            } as ArrayList<String>
//            formItemFieldContentList = jsonObject.getJSONArray("itemDetail")
//        }
        addFormData()
        setupBackButton(binding.widgetTitleBar.imageBack)
        binding.buttonSend.setOnClickListener(this)
    }


    /**
     * 根據string.xml來增加要輸入的欄位
     */
    private fun addFormData() {
        formFieldNameMap.forEach { (english, chinese) ->
            val value = Form::class.java.getDeclaredField(english).let { field ->
                field.isAccessible = true

                val fieldValue = field.get(form)
                fieldValue?.toString() ?: ""
            }

            if (chinese == getString(R.string.deal_status)) {
                val formContentDataSpinnerWidget = FormContentDataSpinnerWidget(
                    activity = this,
                    spinnerList = resources.getStringArray(R.array.deal_status)
                        .toList() as ArrayList<String>,
                    fieldName = chinese,
                    fieldContent = value
                )
                binding.linearFormData.addView(formContentDataSpinnerWidget)
            } else {
                val formContentDataWidget = FormContentDataWidget(
                    activity = this,
                    fieldName = chinese,
                    fieldContent = value.toString()
                )
                binding.linearFormData.addView(formContentDataWidget)
            }
        }
        Timber.d(form.toJsonString())
        form.itemDetails?.forEachIndexed { index, itemDetail ->
                val formGoodsDataWidget =
                    FormGoodsDataWidget(
                        activity = this@FormContentActivity,
                        itemDetail = itemDetail,
                        listener = this@FormContentActivity
                    )
                binding.linearItemData.addView(formGoodsDataWidget)
        }
    }

    /**
     * 點擊確認
     */
    private fun onClickSend() {
        var form = Form()
        val itemDetailList = ArrayList<ItemDetail>()
        for (formGoodsDataWidget in binding.linearItemData) {
            itemDetailList.add((formGoodsDataWidget as FormGoodsDataWidget).itemDetail)
        }
        for (formGoodsDataWidget in binding.linearFormData) {
            var fieldName =
            getEnglishFieldName<Form>((binding.linearFormData as FormContentData).fieldName)
            var value = (binding.linearFormData as FormContentData).content
            setPropertyValue(form, fieldName!!, value)
        }
        form.itemDetails = itemDetailList

        var dealStatus = form.dealStatus
        // 調撥需根據receivingDept(收方單位)和receivingLocation(收料地點)來判斷是進貨還是出貨
        var transferStatus = transferStatus(
            reportTitle == getString(R.string.transfer_form),
            form
        )

        var formEntity = FormEntity()
        formEntity.formNumber = form.formNumber.toString()
        formEntity.formContent = form.toJsonString()

        // 如果表單是交貨、退料、進貨調撥並且處理狀態是處理完成的話要判斷表單中的貨物是否已經全部入庫
        if ((reportTitle == getString(R.string.delivery_form) ||
                    reportTitle == getString(R.string.returning_form) ||
                    transferStatus == transferInput) && dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    viewModel.formRepository.tempWaitInputGoods.value!!,
                    itemDetailList,
                    form.formNumber.toString(),
                    reportTitle
                )
            ) {

                // 如果是退料單取得目前日期並轉換為民國年份
                if(reportTitle == getString(R.string.returning_form) && form.receivedDate != ""){
                    val currentDate = LocalDate.now()
                    val rocYear = currentDate.year - 1911
                    val formattedDate = String.format("%03d/%02d/%02d", rocYear, currentDate.monthValue, currentDate.dayOfMonth)
                    form.receivedDate = formattedDate
                    formEntity.formContent = form.toJsonString()
                }

                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordList(
                    viewModel.getInsertGoodsFromTempWaitDealGoods(
                        itemDetailArray,
                        formContentJsonObject.getString("formNumber"),
                        reportTitle
                    )
                )
                viewModel.inputStorageContent(
                    reportTitle,
                    form.formNumber.toString()
                )
                updateForm(formEntity)
                updateTempWaitInputGoods(
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
                finish()
            } else {
                showToast(this, "貨物未處理完成!")
            }
            // 如果表單是領料、出貨調撥並且處理狀態是處理完成的話要判斷表單中的貨物是否已經全部出庫
        } else if ((reportTitle == pickingFormName ||
                    transferStatus == transferOutput) && dealStatus == getString(R.string.complete_deal)
        ) {
            if (isMaterialAlreadyInput(
                    viewModel.formRepository.tempWaitOutputGoods.value!!,
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
            ) {
                SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordList(
                    viewModel.getInsertGoodsFromTempWaitDealGoods(
                        itemDetailArray,
                        formContentJsonObject.getString("formNumber"),
                        reportTitle
                    )
                )
                viewModel.outputStorageContent(
                    formContentJsonObject.getString("reportTitle"),
                    formContentJsonObject.getString("formNumber")
                )
                updateForm(formEntity)
                updateTempWaitOutputGoods(
                    itemDetailArray,
                    formContentJsonObject.getString("formNumber"),
                    reportTitle
                )
                finish()
            } else {
                showToast(this, "貨物未處理完成!")
            }
        } else {
            updateForm(formEntity)
            finish()
        }
    }


    /**
     * 更新表單和room
     * @param formEntity 更新的表單資訊
     */
    fun updateForm(formEntity: FormEntity) {
        SqlDatabase.getInstance().getFormDao().insertOrUpdate(formEntity)
        FormRepository.getInstance(this).loadRecord()
    }


    /**
     * 判斷進貨類表單(交貨、退料、調撥)表單中的貨物是否已經放入暫存入庫清單(tempWaitInputGoods)
     * @param itemDetailList 要確認的貨物陣列
     * @param targetFormNumber 表單代號
     * @param targetReportTitle 表單名稱
     * @return 回傳Boolean
     */
    fun isMaterialAlreadyInput(
        tempWaitGoods: ArrayList<StorageRecordEntity>,
        itemDetailList: ArrayList<ItemDetail>,
        targetFormNumber: String,
        targetReportTitle: String
    ): Boolean {
        for (i in 0 until itemDetailList.size) {
            val itemDetail = itemDetailList[i]
            var totalQuantity = 0
            for (storageContentEntity in tempWaitGoods) {
                // 檢查條件
                if (
                    storageContentEntity.formNumber == targetFormNumber &&
                    storageContentEntity.reportTitle == targetReportTitle &&
                    JSONObject(storageContentEntity.itemInformation).getString("number") == itemDetail.number
                ) {
                    totalQuantity += JSONObject(storageContentEntity.itemInformation).getInt("quantity")
                }
            }
            if (isInput) {
                if (totalQuantity < itemDetail.receivedQuantity!!) {
                    return false
                }
            } else {
                if (totalQuantity < itemDetail.actualQuantity!!) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 確定入庫後要把暫存入庫清單(tempWaitInputGoods)中關於表單的貨物刪除
     * @param itemDetailArray 要刪除的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun updateTempWaitInputGoods(
        itemDetailArray: JSONArray,
        formNumber: String,
        reportTitle: String
    ) {
        val currentList = viewModel.formRepository.tempWaitInputGoods.value ?: ArrayList()
        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            val targetNumber = itemDetail.getString("number")

            // 移除 tempWaitInputGoods 中符合条件的项
            currentList.removeIf { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        jsonStringToJson(entity.itemInformation)["number"].toString() == targetNumber
            }
        }
        // 更新暫存進貨列表
        viewModel.formRepository.tempWaitInputGoods.postValue(currentList)
    }


    /**
     * 確定出庫後要把暫存出庫清單(tempWaitInputGoods)中關於表單的貨物刪除
     * @param itemDetailArray 要刪除的貨物陣列
     * @param formNumber 表單代號
     * @param reportTitle 表單名稱
     */
    fun updateTempWaitOutputGoods(
        itemDetailArray: JSONArray,
        formNumber: String,
        reportTitle: String
    ) {
        val currentList = viewModel.formRepository.tempWaitOutputGoods.value ?: ArrayList()
        for (i in 0 until itemDetailArray.length()) {
            val itemDetail = itemDetailArray.getJSONObject(i)
            val targetNumber = itemDetail.getString("number")

            // 移除 tempWaitInputGoods 中符合条件的项
            currentList.removeIf { entity ->
                entity.formNumber == formNumber &&
                        entity.reportTitle == reportTitle &&
                        jsonStringToJson(entity.itemInformation)["number"].toString() == targetNumber
            }
        }
        // 更新暫存出貨列表
        viewModel.formRepository.tempWaitOutputGoods.postValue(currentList)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageBack -> {
                onBackPressedCallback.handleOnBackPressed()
            }

            R.id.buttonSend -> {
                onClickSend()
            }

            R.id.imageAdd -> {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
                onAddGoodsClick()
            }
        }
    }


    /**
     * 點擊新增貨物的按鈕後跳出Dialog輸入新增的貨物資訊
     */
    override fun onAddGoodsClick() {
        val goodsDialog = GoodsDialog(true, formItemFieldNameList, formItemFieldNameEngList, this)
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }

    override fun onDeleteGoodsClick(view: View) {
        if (view.id == R.id.imageDelete) {
            // 抓imageDelete的父層，這邊需要跨三層
            val parentItem = view.parent.parent.parent as View
            // 刪除指定列
            binding.linearItemData.removeView(parentItem)
        }
    }

    /**
     * 點擊已有的貨物，在Dialog中顯示貨物資訊
     */
    override fun onGoodsColClick(
        itemDetail: ItemDetail,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
        val goodsDialog = GoodsDialog(
            false,
            formItemFieldNameList,
            formItemFieldNameEngList,
            this,
            itemDetail,
            formGoodsDataWidget
        )
        goodsDialog.show(supportFragmentManager, "GoodsDialog")
    }


    /**
     * 在Dialog中輸入完新增的貨物資訊並送出後，新增一列
     */
    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {
//        val formGoodsDataWidget =
//            FormGoodsDataWidget(this@FormContentActivity, formItemJson, this@FormContentActivity)
//        // 創建一個點擊事件
//        binding.linearItemData.addView(formGoodsDataWidget)
//        //新增後能下移顯示新增的widget
//        binding.scrollViewData.post {
//            binding.scrollViewData.fullScroll(View.FOCUS_DOWN)
//        }
    }

    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
        formGoodsDataWidget.binding.textMaterialName.text = formItemJson.getString("materialName")
        formGoodsDataWidget.binding.textMaterialNumber.text = formItemJson.getString("materialNumber")
        formGoodsDataWidget.binding.textMaterialSpec.text = formItemJson.getString("materialSpec")
        formGoodsDataWidget.binding.textMaterialUnit.text = formItemJson.getString("materialUnit")
//        formGoodsDataWidget.itemDetail = formItemJson
        binding.scrollViewData.smoothScrollTo(0, formGoodsDataWidget.top)
    }
}