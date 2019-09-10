package dev.mesmoustaches.data.model.getout


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.*

@Entity(tableName = "filters_group")
data class FacetGroup(
    @SerializedName("facets")
    @ColumnInfo(name = "facets")
    val facets: List<Facet?>?,
    @SerializedName("name")
    @ColumnInfo(name = "path")
    @PrimaryKey
    val id: String
)

class FacetListConverter {
    @TypeConverter
    fun stringToFacetList(data: String?): List<Facet>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Facet>>() {

        }.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun facetListToString(someObjects: List<Facet>?): String? {
        val gson = Gson()
        return gson.toJson(someObjects)
    }
}