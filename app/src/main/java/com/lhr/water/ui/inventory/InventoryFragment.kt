package com.lhr.water.ui.inventory

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentInventoryBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.util.adapter.InventoryAdapter
import com.lhr.water.util.dialog.InventoryGoodsDialog
import com.lhr.water.util.widget.FormGoodsDataWidget
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber


class InventoryFragment : BaseFragment(), View.OnClickListener, InventoryAdapter.Listener, InventoryGoodsDialog.Listener {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InventoryViewModel by viewModels { viewModelFactory }
    private lateinit var inventoryAdapter: InventoryAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(requireContext()) }

    private var formFieldNameList = ArrayList<String>() //表單欄位
    private var formFieldNameEngList = ArrayList<String>() //表單欄位英文名
    private var formFieldContentList = ArrayList<String>() //表單欄位內容
    private var formItemFieldContentList: JSONArray? = null //貨物欄位內容

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        formRepository.inventoryEntities.observe(viewLifecycleOwner) { inventoryEntities ->
            inventoryAdapter.submitList(inventoryEntities)
        }
    }

    private fun initView() {

        formFieldNameList = resources.getStringArray(R.array.inventory_form_field_name)
            .toList() as ArrayList<String>
        formFieldNameEngList = resources.getStringArray(R.array.inventory_form_field_name_eng)
                .toList() as ArrayList<String>

        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.search_inventory)
        binding.widgetTitleBar.imageAdd.visibility = View.VISIBLE
        binding.widgetTitleBar.imageBackup.visibility = View.VISIBLE
        initRecyclerView()
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本改變之前執行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本改變過程中執行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改變之後執行的操作
//                formRepository.searchFormNumber.postValue(s.toString())
            }
        })

        binding.widgetTitleBar.imageAdd.setOnClickListener(this)
        binding.widgetTitleBar.imageBackup.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        inventoryAdapter = InventoryAdapter(this, requireContext())
        binding.recyclerInventory.adapter = inventoryAdapter
        binding.recyclerInventory.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageAdd -> {
                pickFile.launch("application/json")
            }
            R.id.imageBackup -> {

            }
        }
    }

    /**
     * 表單列表點擊
     * @param json 被點擊的列資料
     */
    override fun onItemClick(json: JSONObject) {
        val extractedValues = ArrayList<String>()
        for (fieldName in formFieldNameEngList) {
            try {
                val value: String = json.getString(fieldName)
                extractedValues.add(value)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val inventoryGoodsDialog = InventoryGoodsDialog(formFieldNameList, formFieldNameEngList, this, formItemFieldContentList = extractedValues)
        inventoryGoodsDialog.show(requireActivity().supportFragmentManager, "GoodsDialog")
    }

    /**
     * 選擇JSON檔案並讀取
     */
    private val pickFile =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.updateFormData(requireContext(), it)
            }
        }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
        callback.remove()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
        formGoodsDataWidget.binding.textMaterialName.text = formItemJson.getString("materialName")
        formGoodsDataWidget.binding.textMaterialNumber.text = formItemJson.getString("materialNumber")
        formGoodsDataWidget.binding.textMaterialSpec.text = formItemJson.getString("materialSpec")
        formGoodsDataWidget.binding.textMaterialUnit.text = formItemJson.getString("materialUnit")
        formGoodsDataWidget.formItemJson = formItemJson
    }
}