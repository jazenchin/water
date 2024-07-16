package com.lhr.water.util

import com.lhr.water.R
import com.lhr.water.room.RegionEntity
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.inventoryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName

val API_BASE: String = "http://localhost:8081"

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

object FormField {
    // 交貨通知單欄位
    private val deliveryFormField = arrayOf(
        "id",
        "deliveryStatus",
        "reportId",
        "reportTitle",
        "date",
        "formNumber",
        "underwriter",
        "ano",
        "contractNumber",
        "deliveryDay",
        "deliveryDate",
        "extendDate1",
        "extendDate2",
        "extendDate3",
        "receiptDept",
        "deliverylocation",
        "extendNo1",
        "extendNo2",
        "extendNo3",
        "projectNumber",
        "contact",
        "contactPhone",
        "applyNo",
        "sumAddition"
    )
    private val deliveryMaterialField = arrayOf(
        "number",
        "itemNo",
        "batch",
        "no",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "length",
        "deliveryQuantity",
        "receivedQuantity",
        "price",
        "itemCost"
    )

    // 材料領料單欄位
    private val pickFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "issuingUnit",
        "pickingDate",
        "pickingDept",
        "formNumber",
        "originalVoucherNumber",
        "costAllocationUnit",
        "accountingSubject",
        "systemCode",
        "usageCode",
        "projectNumber",
        "projectName",
        "caseNumber"
    )
    private val pickMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "requestedQuantity",
        "actualQuantity",
        "price",
        "itemCost"
    )

    // 材料調撥單欄位
    private val transferFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "transferringTransferNumber",
        "receivingApplyTransferNumber",
        "receivingTransferNumber",
        "transferringDept",
        "originalVoucherNumber",
        "requiredDate",
        "transferringTransferDate",
        "receivingTransferDate",
        "receivingApplyTransferDate",
        "receivingDept",
        "receivingLocation",
        "contact",
        "contactPhone",
        "transferDescription"
    )
    private val transferMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "approvedQuantity",
        "applyNumber",
        "allocatedQuantity",
        "receivedQuantity"
    )

    // 材料退料單欄位
    private val returningFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "receiptDept",
        "leadNumber",
        "leadDept",
        "formNumber",
        "receivedDate",
        "accountingSubject",
        "costAllocationUnit",
        "returnDept",
        "systemCode",
        "usageCode",
        "projectNumber",
        "projectName",
        "originalVoucherNumber"
    )
    private val returningMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "returnedQuantity",
        "receivedQuantity",
        "stockingPrice"
    )

    // 材料盤點單欄位
    private val inventoryFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "inventoryUnit",
        "deptName",
        "materialNumber",
        "materialUnit",
        "materialName",
        "materialSpec",
        "actualQuantity",
        "checkDate",
        "lastUseDate",
        "approvedDate"
    )


    val formFieldMap: Map<String, Array<Array<String>>> = mapOf(
        deliveryFormName to arrayOf(deliveryFormField, deliveryMaterialField),
        pickingFormName to arrayOf(pickFormField, pickMaterialField),
        transferFormName to arrayOf(transferFormField, transferMaterialField),
        returningFormName to arrayOf(returningFormField, returningMaterialField),
        inventoryFormName to arrayOf(inventoryFormField)
    )
}


val dealStatusList: List<String> = listOf(
    "待處理", "處理中", "處理完成"
)

val fromTitleList: List<String> = listOf(
    deliveryFormName, pickingFormName, transferFormName, returningFormName, inventoryFormName
)

// 1-交貨，2-驗收，3-調撥，4-領料，5-退料，6-盤點
val formTypeMap: Map<String, String> = mapOf(
    deliveryFormName to "1",
    transferFormName to "3",
    pickingFormName to "4",
    returningFormName to "5",
    inventoryFormName to "6"
)

// 1: 已交貨，2: 已驗收，3: 已移出
val materialStatusMap: Map<String, String> = mapOf(
    "1" to "已交貨",
    "2" to "已驗收",
    "3" to "已移出",
)

var markDrawableIdMap = mapOf(
    0 to R.drawable.mark,
    1 to R.drawable.office,
    2 to R.drawable.door,
    3 to R.drawable.stairs,
    4 to R.drawable.elevator,
    5 to R.drawable.wc,
    6 to R.drawable.parking
)

val MapDataList: List<RegionEntity> =
    listOf(
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0761",
            deptName = "大崗山給水廠",
            deptNumber = "0761",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0761",
            deptName = "大崗山給水廠",
            deptNumber = "0761",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D",
            deptName = "屏東區管理處",
            deptNumber = "0D",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D",
            deptName = "屏東區管理處",
            deptNumber = "0D",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D40",
            deptName = "屏東營運所",
            deptNumber = "0D40",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D40",
            deptName = "屏東營運所",
            deptNumber = "0D40",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D43",
            deptName = "恆春營運所",
            deptNumber = "0D43",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D43",
            deptName = "恆春營運所",
            deptNumber = "0D43",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D41",
            deptName = "東港營運所",
            deptNumber = "0D41",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D41",
            deptName = "東港營運所",
            deptNumber = "0D41",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D42",
            deptName = "潮州營運所",
            deptNumber = "0D42",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D42",
            deptName = "潮州營運所",
            deptNumber = "0D42",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D60",
            deptName = "牡丹給水廠",
            deptNumber = "0D60",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D60",
            deptName = "牡丹給水廠",
            deptNumber = "0D60",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D60",
            deptName = "牡丹給水廠",
            deptNumber = "0D60",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D44",
            deptName = "高樹營運所",
            deptNumber = "0D44",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D44",
            deptName = "高樹營運所",
            deptNumber = "0D44",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "屏東區管理處",
            regionNumber = "0D44",
            deptName = "高樹營運所",
            deptNumber = "0D44",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0110",
            deptName = "一區物料課",
            deptNumber = "0110",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0121",
            deptName = "基隆服務所",
            deptNumber = "0121",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0146",
            deptName = "文山營運所",
            deptNumber = "0146",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0143",
            deptName = "汐止營運所",
            deptNumber = "0143",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0144",
            deptName = "淡水營運所",
            deptNumber = "0144",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0144",
            deptName = "淡水營運所",
            deptNumber = "0144",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0141",
            deptName = "瑞芳營運所",
            deptNumber = "0141",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0145",
            deptName = "萬里金山營運所",
            deptNumber = "0145",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第一區管理處",
            regionNumber = "0147",
            deptName = "貢寮雙溪營運所",
            deptNumber = "0147",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0764",
            deptName = "坪頂給水廠",
            deptNumber = "0764",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0761",
            deptName = "大崗山給水廠",
            deptNumber = "0761",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0761",
            deptName = "大崗山給水廠",
            deptNumber = "0761",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0726",
            deptName = "岡山服務所",
            deptNumber = "0726",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0766",
            deptName = "拷潭給水廠",
            deptNumber = "0766",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0741",
            deptName = "旗山營運所",
            deptNumber = "0741",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0727",
            deptName = "楠梓服務所",
            deptNumber = "0727",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0762",
            deptName = "澄清湖給水廠",
            deptNumber = "0762",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0762",
            deptName = "澄清湖給水廠",
            deptNumber = "0762",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0749",
            deptName = "澎湖營運所",
            deptNumber = "0749",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0749",
            deptName = "澎湖營運所",
            deptNumber = "0749",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0749",
            deptName = "澎湖營運所",
            deptNumber = "0749",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0725",
            deptName = "路竹服務所",
            deptNumber = "0725",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0729",
            deptName = "高雄服務所",
            deptNumber = "0729",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0769",
            deptName = "高雄給水廠",
            deptNumber = "0769",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0724",
            deptName = "鳳山服務所",
            deptNumber = "0724",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第七區管理處",
            regionNumber = "0767",
            deptName = "鳳山給水廠",
            deptNumber = "0767",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "0343",
            deptName = "湖口營運所",
            deptNumber = "0343",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "0348",
            deptName = "竹北營運所",
            deptNumber = "0348",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "0341",
            deptName = "竹東營運所",
            deptNumber = "0341",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "03",
            deptName = "第三區管理處",
            deptNumber = "03",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "03",
            deptName = "第三區管理處",
            deptNumber = "03",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "0345",
            deptName = "苗栗營運所",
            deptNumber = "0345",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第三區管理處",
            regionNumber = "0349",
            deptName = "通霄銅鑼營運所",
            deptNumber = "0349",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第九區管理處",
            regionNumber = "0910",
            deptName = "九區物料課",
            deptNumber = "0910",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第九區管理處",
            regionNumber = "0946",
            deptName = "玉里營運所",
            deptNumber = "0946",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第九區管理處",
            regionNumber = "0946",
            deptName = "玉里營運所",
            deptNumber = "0946",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第九區管理處",
            regionNumber = "09",
            deptName = "第九區管理處",
            deptNumber = "09",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第九區管理處",
            regionNumber = "0944",
            deptName = "鳳林營運所",
            deptNumber = "0944",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0210",
            deptName = "二區物料課",
            deptNumber = "0210",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0210",
            deptName = "二區物料課",
            deptNumber = "0210",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0263",
            deptName = "大湳給水廠",
            deptNumber = "0263",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0262",
            deptName = "平鎮給水廠",
            deptNumber = "0262",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0229",
            deptName = "林口服務所",
            deptNumber = "0229",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0244",
            deptName = "龍潭營運所",
            deptNumber = "0244",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0244",
            deptName = "龍潭營運所",
            deptNumber = "0244",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第二區管理處",
            regionNumber = "0222",
            deptName = "龜山服務所",
            deptNumber = "0222",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0510",
            deptName = "五區物料課",
            deptNumber = "0510",
            mapSeq = 4
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0544",
            deptName = "北港營運所",
            deptNumber = "0544",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0551",
            deptName = "古坑營運所",
            deptNumber = "0551",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0549",
            deptName = "台西營運所",
            deptNumber = "0549",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0561",
            deptName = "嘉義給水廠",
            deptNumber = "0561",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0525",
            deptName = "斗六服務所",
            deptNumber = "0525",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0528",
            deptName = "斗南服務所",
            deptNumber = "0528",
            mapSeq = 6
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0554",
            deptName = "新港營運所",
            deptNumber = "0554",
            mapSeq = 7
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0524",
            deptName = "朴子服務所",
            deptNumber = "0524",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0530",
            deptName = "民雄營運所",
            deptNumber = "0530",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0530",
            deptName = "民雄營運所",
            deptNumber = "0530",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0560",
            deptName = "竹崎營運所",
            deptNumber = "0560",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "05",
            deptName = "第五區管理處",
            deptNumber = "05",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "05",
            deptName = "第五區管理處",
            deptNumber = "05",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "05",
            deptName = "第五區管理處",
            deptNumber = "05",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "05",
            deptName = "第五區管理處",
            deptNumber = "05",
            mapSeq = 4
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0523",
            deptName = "義竹服務所",
            deptNumber = "0523",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0527",
            deptName = "虎尾服務所",
            deptNumber = "0527",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0526",
            deptName = "西螺服務所",
            deptNumber = "0526",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第五區管理處",
            regionNumber = "0564",
            deptName = "雲林給水廠",
            deptNumber = "0564",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第八區管理處",
            regionNumber = "0800",
            deptName = "八區物料課",
            deptNumber = "0800",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第八區管理處",
            regionNumber = "0800",
            deptName = "八區物料課",
            deptNumber = "0800",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第八區管理處",
            regionNumber = "0862",
            deptName = "深溝給水廠",
            deptNumber = "0862",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0627",
            deptName = "佳里服務所",
            deptNumber = "0627",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0664",
            deptName = "南化給水廠",
            deptNumber = "0664",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0664",
            deptName = "南化給水廠",
            deptNumber = "0664",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0620",
            deptName = "台南市服務所",
            deptNumber = "0620",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0661",
            deptName = "台南給水廠",
            deptNumber = "0661",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0632",
            deptName = "新市服務所",
            deptNumber = "0632",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0642",
            deptName = "新營營運所",
            deptNumber = "0642",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0626",
            deptName = "歸仁服務所",
            deptNumber = "0626",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0630",
            deptName = "永康服務所",
            deptNumber = "0630",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0663",
            deptName = "烏山頭給水廠",
            deptNumber = "0663",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0644",
            deptName = "玉井營運所",
            deptNumber = "0644",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0643",
            deptName = "白河營運所",
            deptNumber = "0643",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "06",
            deptName = "第六區管理處",
            deptNumber = "06",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第六區管理處",
            regionNumber = "0641",
            deptName = "麻豆服務所",
            deptNumber = "0641",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B48",
            deptName = "二林營運所",
            deptNumber = "0B48",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B48",
            deptName = "二林營運所",
            deptNumber = "0B48",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B44",
            deptName = "二水營運所",
            deptNumber = "0B44",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B44",
            deptName = "二水營運所",
            deptNumber = "0B44",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B44",
            deptName = "二水營運所",
            deptNumber = "0B44",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B43",
            deptName = "北斗營運所",
            deptNumber = "0B43",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B43",
            deptName = "北斗營運所",
            deptNumber = "0B43",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B10",
            deptName = "十一區物料課",
            deptNumber = "0B10",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B10",
            deptName = "十一區物料課",
            deptNumber = "0B10",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B10",
            deptName = "十一區物料課",
            deptNumber = "0B10",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B10",
            deptName = "十一區物料課",
            deptNumber = "0B10",
            mapSeq = 4
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B42",
            deptName = "員林營運所",
            deptNumber = "0B42",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B42",
            deptName = "員林營運所",
            deptNumber = "0B42",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B42",
            deptName = "員林營運所",
            deptNumber = "0B42",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B42",
            deptName = "員林營運所",
            deptNumber = "0B42",
            mapSeq = 4
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B61",
            deptName = "彰化給水廠",
            deptNumber = "0B61",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B61",
            deptName = "彰化給水廠",
            deptNumber = "0B61",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B61",
            deptName = "彰化給水廠",
            deptNumber = "0B61",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B51",
            deptName = "溪湖營運所",
            deptNumber = "0B51",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B51",
            deptName = "溪湖營運所",
            deptNumber = "0B51",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B52",
            deptName = "花壇營運所",
            deptNumber = "0B52",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B52",
            deptName = "花壇營運所",
            deptNumber = "0B52",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B47",
            deptName = "鹿港營運所",
            deptNumber = "0B47",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B47",
            deptName = "鹿港營運所",
            deptNumber = "0B47",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B47",
            deptName = "鹿港營運所",
            deptNumber = "0B47",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十一區管理處",
            regionNumber = "0B47",
            deptName = "鹿港營運所",
            deptNumber = "0B47",
            mapSeq = 4
        ),
        RegionEntity(
            regionName = "第十二區管理處",
            regionNumber = "0C10",
            deptName = "十二區物料課",
            deptNumber = "0C10",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十二區管理處",
            regionNumber = "0C10",
            deptName = "十二區物料課",
            deptNumber = "0C10",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十二區管理處",
            regionNumber = "0C10",
            deptName = "十二區物料課",
            deptNumber = "0C10",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A46",
            deptName = "太麻里營運所",
            deptNumber = "0A46",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A46",
            deptName = "太麻里營運所",
            deptNumber = "0A46",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A45",
            deptName = "成功營運所",
            deptNumber = "0A45",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A45",
            deptName = "成功營運所",
            deptNumber = "0A45",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A45",
            deptName = "成功營運所",
            deptNumber = "0A45",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A43",
            deptName = "池上營運所",
            deptNumber = "0A43",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A43",
            deptName = "池上營運所",
            deptNumber = "0A43",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A",
            deptName = "第十區管理處",
            deptNumber = "0A",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第十區管理處",
            regionNumber = "0A",
            deptName = "第十區管理處",
            deptNumber = "0A",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0444",
            deptName = "南投營運所",
            deptNumber = "0444",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0453",
            deptName = "埔里營運所",
            deptNumber = "0453",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0453",
            deptName = "埔里營運所",
            deptNumber = "0453",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0443",
            deptName = "大甲營運所",
            deptNumber = "0443",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0426",
            deptName = "大里服務所",
            deptNumber = "0426",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0455",
            deptName = "大雅營運所",
            deptNumber = "0455",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0448",
            deptName = "東勢營運所",
            deptNumber = "0448",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0447",
            deptName = "水里營運所",
            deptNumber = "0447",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0454",
            deptName = "沙鹿營運所",
            deptNumber = "0454",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0442",
            deptName = "清水營運所",
            deptNumber = "0442",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0452",
            deptName = "烏日營運所",
            deptNumber = "0452",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0446",
            deptName = "竹山營運所",
            deptNumber = "0446",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "04",
            deptName = "第四區管理處",
            deptNumber = "04",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "04",
            deptName = "第四區管理處",
            deptNumber = "04",
            mapSeq = 2
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "04",
            deptName = "第四區管理處",
            deptNumber = "04",
            mapSeq = 3
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0445",
            deptName = "草屯營運所",
            deptNumber = "0445",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0423",
            deptName = "豐原服務所",
            deptNumber = "0423",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0461",
            deptName = "豐原給水廠",
            deptNumber = "0461",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0451",
            deptName = "霧峰營運所",
            deptNumber = "0451",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "第四區管理處",
            regionNumber = "0463",
            deptName = "鯉魚潭給水廠",
            deptNumber = "0463",
            mapSeq = 1
        ),
        RegionEntity(
            regionName = "總管理處",
            regionNumber = "00",
            deptName = "總管理處",
            deptNumber = "00",
            mapSeq = 1
        ),
    )
