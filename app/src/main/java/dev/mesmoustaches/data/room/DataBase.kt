package dev.mesmoustaches.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.mesmoustaches.data.model.getout.*

@Database(entities = [RecordData::class, FacetGroup::class], version = 3, exportSchema = false)

@TypeConverters(
    FieldConverter::class,
    GeometryConverter::class,
    FacetListConverter::class)

abstract class DataBase : RoomDatabase() {
    abstract fun eventDao(): EventsDao
    abstract fun filterDao(): FilterGroupsDao
}