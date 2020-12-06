package com.dwiyanstudio.nabungkuy.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwiyanstudio.nabungkuy.Constanst
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constanst.TABLE_TABUNGAN_NAME)
data class NabungData(
    @ColumnInfo(name = "nama_tabungan")
    var nama_tabungan: String,
    @ColumnInfo(name = "jumlah_tabungan")
    var jumlah_tabungan: Double,
    @ColumnInfo(name = "tenggat_waktu")
    var tenggat_waktu: String,
    @ColumnInfo(name = "target_waktu")
    var target_waktu: Long,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "deskripsi")
    var desc: String,
    @ColumnInfo(name = "jumlah_sisa_tabungan")
    var sisa_tabungan: Double,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable