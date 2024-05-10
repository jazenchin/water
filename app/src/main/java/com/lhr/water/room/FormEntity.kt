package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.lhr.water.data.form.BaseForm
import java.io.Serializable

@Entity(
    tableName = SqlModel.FORM_TABLE_NAME,
    indices = [Index(value = [SqlModel.id])]
)
class FormEntity(
    formNumber: String,
    dealStatus: String,
    reportId: String,
    reportTitle: String,
    dealTime: String,
    date: String,
    formContent: String,
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = formNumber

    @ColumnInfo(name = SqlModel.dealStatus, typeAffinity = ColumnInfo.TEXT)
    var dealStatus = dealStatus

    @ColumnInfo(name = SqlModel.reportId, typeAffinity = ColumnInfo.TEXT)
    var reportId = reportId

    @ColumnInfo(name = SqlModel.reportTitle, typeAffinity = ColumnInfo.TEXT)
    var reportTitle = reportTitle

    @ColumnInfo(name = SqlModel.dealTime, typeAffinity = ColumnInfo.TEXT)
    var dealTime = dealTime

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    var date = date

    @ColumnInfo(name = SqlModel.formContent, typeAffinity = ColumnInfo.TEXT)
    var formContent = formContent

    companion object {
        fun convertFormToFormEntities(formList: List<BaseForm>): List<FormEntity> {
            val gson = Gson()
            val formEntities = ArrayList<FormEntity>()
            for (form in formList) {
                val formContentJson = gson.toJson(form)
                val formEntity = FormEntity(
                    form.formNumber,
                    form.dealStatus,
                    form.reportId,
                    form.reportTitle,
                    form.dealTime,
                    form.date,
                    formContentJson
                )
                formEntities.add(formEntity)
            }
            return formEntities
        }
    }
}