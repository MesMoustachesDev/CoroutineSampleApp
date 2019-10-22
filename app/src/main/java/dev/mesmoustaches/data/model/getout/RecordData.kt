package dev.mesmoustaches.data.model.getout


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dev.mesmoustaches.domain.model.EventDomain
import dev.mesmoustaches.domain.model.toLinkBalise
import java.text.SimpleDateFormat

@Entity(tableName = "events")
data class RecordData(
    @ColumnInfo(name = "datasetid")
    @SerializedName("datasetid")
    val datasetid: String?,
    @ColumnInfo(name = "fields")
    @SerializedName("fields")
    val fields: Fields?,
    @ColumnInfo(name = "geometry")
    @SerializedName("geometry")
    val geometry: Geometry?,
    @ColumnInfo(name = "record_timestamp")
    @SerializedName("record_timestamp")
    val recordTimestamp: String?,
    @ColumnInfo(name = "recordid")
    @PrimaryKey
    @SerializedName("recordid")
    val recordid: String
)

class FieldConverter {
    @TypeConverter
    fun stringToOffersModel(data: String?): Fields? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        return gson.fromJson(data, Fields::class.java)
    }

    @TypeConverter
    fun offersModelToString(offers: Fields?): String {
        val gson = Gson()
        return gson.toJson(offers)
    }
}

class GeometryConverter {
    @TypeConverter
    fun stringToOffersModel(data: String?): Geometry? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        return gson.fromJson(data, Geometry::class.java)
    }

    @TypeConverter
    fun offersModelToString(offers: Geometry?): String {
        val gson = Gson()
        return gson.toJson(offers)
    }
}

fun RecordData.toDomain(): EventDomain {
/* date formatter in local timezone */
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+00:00'")
    return EventDomain.EventDomainData(
        id = recordid,
        title = fields?.title ?: "",
        description = "${fields?.description ?: ""} <br/><br/><br/>${fields?.priceDetail ?: ""} <br/><br/><br/>${fields?.contactUrl?.toLinkBalise() ?: ""} ",
        timeStamp = sdf.parse(recordTimestamp).time,
        dateText = fields?.dateDescription ?: "",
        image = fields?.coverUrl,
        address = "${fields?.addressStreet}\n${fields?.addressZipcode} ${fields?.addressCity}",
        position = (geometry?.coordinates?.get(1) ?: 0.0) to (geometry?.coordinates?.get(0) ?: 0.0),
        phone = fields?.contactPhone,
        mail = fields?.contactMail,
        facebook = fields?.contactFacebook
    )
}