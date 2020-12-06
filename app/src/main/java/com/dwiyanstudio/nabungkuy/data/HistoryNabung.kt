package com.dwiyanstudio.nabungkuy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwiyanstudio.nabungkuy.Constanst

@Entity(tableName = Constanst.TABLE_HISTORY_TABUNGAN)
data class HistoryNabung(
    @ColumnInfo(name = "jumlah_nabung")
    var jumlah_nabung: Double,
    @ColumnInfo(name = "id_tabungan")
    var id_tabungan: Int,
    @ColumnInfo(name = "tanggal_nabung")
    var tanggal_nabung: String,
    @ColumnInfo(name = "nama_tabungan")
    var nama_tabungan: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)