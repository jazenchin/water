package com.lhr.water.util

import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.inventoryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName

val API_BASE: String = "http://localhost:8081"
object MapPageStatus {
    const val RegionPage = "Region"
    const val MapPage = "Map"
}

object TransferStatus {
    const val transferInput = "transferInput"
    const val transferOutput = "transferOutput"
    const val notTransfer = "notTransfer"
}

object FormName {
    const val deliveryFormName = "交貨通知單"
    const val pickingFormName = "材料領料單"
    const val transferFormName = "材料調撥單"
    const val returningFormName = "材料退料單"
    const val inventoryFormName = "材料盤點單"
}


object DealStatus {
    const val waitDeal = "待處理"
    const val nowDeal = "處理中"
    const val completeDeal = "處理完成"
}

object FormField {
    // 交貨通知單欄位
    private val deliveryFormField = arrayOf("id", "deliveryStatus", "reportId", "reportTitle", "date", "formNumber", "underwriter", "ano", "contractNumber", "deliveryDay", "deliveryDate", "extendDate1", "extendDate2", "extendDate3", "receiptDept", "deliverylocation", "extendNo1", "extendNo2", "extendNo3", "projectNumber", "contact", "contactPhone", "applyNo", "sumAddition")
    private val deliveryMaterialField = arrayOf("number", "itemNo", "batch", "no", "materialNumber", "materialName", "materialSpec", "materialUnit", "length", "deliveryQuantity", "receivedQuantity", "price", "itemCost")
    // 材料領料單欄位
    private val pickFormField = arrayOf("id", "dealStatus", "reportId", "reportTitle", "date", "issuingUnit", "pickingDate", "pickingDept", "formNumber", "originalVoucherNumber", "costAllocationUnit", "accountingSubject", "systemCode", "usageCode", "projectNumber", "projectName", "caseNumber")
    private val pickMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "requestedQuantity", "actualQuantity", "price", "itemCost")
    // 材料調撥單欄位
    private val transferFormField = arrayOf("id", "dealStatus", "reportId", "reportTitle", "date", "transferringTransferNumber", "receivingApplyTransferNumber", "receivingTransferNumber", "transferringDept", "originalVoucherNumber", "requiredDate", "transferringTransferDate", "receivingTransferDate", "receivingApplyTransferDate", "receivingDept", "receivingLocation", "contact", "contactPhone", "transferDescription")
    private val transferMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "approvedQuantity", "applyNumber", "allocatedQuantity", "receivedQuantity")
    // 材料退料單欄位
    private val returningFormField = arrayOf("id", "dealStatus", "reportId", "reportTitle", "date", "receiptDept", "leadNumber", "leadDept", "formNumber", "receivedDate", "accountingSubject", "costAllocationUnit", "returnDept", "systemCode", "usageCode", "projectNumber", "projectName", "originalVoucherNumber")
    private val returningMaterialField = arrayOf("number", "materialNumber", "materialName", "materialSpec", "materialUnit", "returnedQuantity", "receivedQuantity", "stockingPrice")
    // 材料盤點單欄位
    private val inventoryFormField = arrayOf("id", "dealStatus", "reportId", "reportTitle", "date", "inventoryUnit", "deptName", "materialNumber", "materialUnit", "materialName", "materialSpec", "actualQuantity", "checkDate", "lastUseDate", "approvedDate")


    val formFieldMap: Map<String, Array<Array<String>>> = mapOf(
        deliveryFormName to arrayOf(deliveryFormField, deliveryMaterialField),
        pickingFormName to arrayOf(pickFormField, pickMaterialField),
        transferFormName to arrayOf(transferFormField, transferMaterialField),
        returningFormName to arrayOf(returningFormField, returningMaterialField),
        inventoryFormName to arrayOf(inventoryFormField)
    )
}