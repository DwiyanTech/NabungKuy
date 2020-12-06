package com.dwiyanstudio.nabungkuy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dwiyanstudio.nabungkuy.data.HistoryNabung
import com.dwiyanstudio.nabungkuy.data.NabungData

@Database(entities = [NabungData::class, HistoryNabung::class], version = 6)
abstract class NabungDatabase : RoomDatabase() {
    abstract fun nabungDao(): TabunganDao
}