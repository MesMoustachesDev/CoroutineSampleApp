package dev.mesmoustaches.data.model.getout


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

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