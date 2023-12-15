package com.lhr.water.ui.goods

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.data.StorageDetail
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.getCurrentDate

class GoodsViewModel(
    context: Context,
    formRepository: FormRepository,
    regionRepository: RegionRepository
) : AndroidViewModel(context.applicationContext as APP) {

    var regionRepository = regionRepository
    var formRepository = formRepository

    fun getWaitInputGoods(): ArrayList<WaitDealGoodsData> {
        return formRepository.waitInputGoods.value!!
    }

    fun getWaitOutputGoods(): ArrayList<WaitDealGoodsData> {
        return formRepository.waitOutputGoods.value!!
    }

    fun getRegionNameList(): ArrayList<String> {
        return regionRepository.getRegionNameList()
    }

    fun getMapNameList(regionName: String): ArrayList<String> {
        return regionRepository.getMapNameList(regionName)
    }

    fun getStorageNameList(regionName: String, mapName: String): ArrayList<StorageDetail> {
        return regionRepository.getStorageDetailList(regionName, mapName)
    }


    /**
     * 將選擇貨物加入儲櫃中並更新資料庫和表單列表
     */
    fun inputGoods(
        waitDealGoodsData: WaitDealGoodsData,
        region: String,
        map: String,
        storageNum: String
    ) {
        // 需要為貨物加上地區、地圖、儲櫃代號、報表名稱、報表代號、入庫時間欄位
        var waitInputGoodsJson = waitDealGoodsData.itemInformation

        waitInputGoodsJson.put("regionName", region)
        waitInputGoodsJson.put("mapName", map)
        waitInputGoodsJson.put("storageNum", storageNum)
        waitInputGoodsJson.put("formNumber", waitDealGoodsData.formNumber)
        waitInputGoodsJson.put("reportTitle", waitDealGoodsData.reportTitle)
        // 入庫時間記錄到民國年月日就好
        waitInputGoodsJson.put("inputDate", getCurrentDate())

        var storageContentEntity = StorageContentEntity()
        storageContentEntity.regionName = region
        storageContentEntity.mapName = map
        storageContentEntity.storageNum = storageNum
        storageContentEntity.formNumber = waitDealGoodsData.formNumber
        storageContentEntity.reportTitle = waitDealGoodsData.reportTitle
        storageContentEntity.itemInformation = waitInputGoodsJson.toString()

        SqlDatabase.getInstance().getStorageContentDao()
            .insertStorageItem(storageContentEntity)
        formRepository.updateWaitInputGoods(formRepository.formRecordList.value!!)
    }
}