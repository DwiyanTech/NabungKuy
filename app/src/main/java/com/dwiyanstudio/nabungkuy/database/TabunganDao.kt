package com.dwiyanstudio.nabungkuy.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.data.HistoryNabung
import com.dwiyanstudio.nabungkuy.data.JumlahTabungan
import com.dwiyanstudio.nabungkuy.data.NabungData
import io.reactivex.Single


@Dao
interface TabunganDao {

    @Query("SELECT * FROM ${Constanst.TABLE_TABUNGAN_NAME}")
    fun getAllTabungan(): LiveData<List<NabungData>>


    @Query("SELECT SUM(jumlah_tabungan) AS jumlah_tabung FROM ${Constanst.TABLE_TABUNGAN_NAME}")
    fun getJumlahAllTabungan() : LiveData<JumlahTabungan>

    @Insert
    fun insertTabungan(nabungData: NabungData): Single<Long>

    @Query("SELECT * FROM ${Constanst.TABLE_HISTORY_TABUNGAN} WHERE id_tabungan= :id_tabungan")
    fun getHistoryTabungan(id_tabungan: Int): LiveData<List<HistoryNabung>>

    @Query("SELECT * FROM ${Constanst.TABLE_HISTORY_TABUNGAN}")
    fun getHistoryAllTabungan(): LiveData<List<HistoryNabung>>

    @Insert
    fun insertHistoryTabungan(historyNabung: HistoryNabung): Single<Long>

    @Query("UPDATE ${Constanst.TABLE_TABUNGAN_NAME} SET jumlah_sisa_tabungan= :jumlah_sisa WHERE id= :id_tabungan")
    fun updateJumlahSisaTabungan(jumlah_sisa: Int, id_tabungan: Int): Single<Int>

    @Query("UPDATE ${Constanst.TABLE_TABUNGAN_NAME} SET nama_tabungan= :nama_tabungan,jumlah_tabungan= :jumlah_tabungan,tenggat_waktu= :tenggat_waktu,status= :status,deskripsi=:desc WHERE id= :id_tabungan")
    fun updateTabungan(
        nama_tabungan: String,
        jumlah_tabungan: Double,
        tenggat_waktu: String,
        status: String,
        desc: String,
        id_tabungan: Int
    ): Single<Int>

    @Delete
    fun deleteTabungan(nabungData: NabungData): Single<Int>

    @Query("DELETE FROM ${Constanst.TABLE_HISTORY_TABUNGAN} WHERE id_tabungan= :id_tabungan")
    fun deleteHistoryTabungan(id_tabungan: Int): Single<Int>
}