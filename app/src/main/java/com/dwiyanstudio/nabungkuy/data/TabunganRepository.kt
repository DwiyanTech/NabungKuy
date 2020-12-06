package com.dwiyanstudio.nabungkuy.data

import androidx.lifecycle.LiveData
import com.dwiyanstudio.nabungkuy.database.NabungDatabase
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TabunganRepository @Inject constructor(nabungDatabase: NabungDatabase) {
    private val compositeDisposable = CompositeDisposable()
    val nabungDao = nabungDatabase.nabungDao()

    fun getDataTabungan(): LiveData<List<NabungData>> = nabungDao.getAllTabungan()

    val getJumlahSumTabungan = nabungDao.getJumlahAllTabungan()

    fun insertTabungan(nabungData: NabungData, responseListener: ResponseListener) {
        val dispose = nabungDao.insertTabungan(nabungData)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success -> responseListener.onSuccess() }, { error ->
                error.message?.let {
                    responseListener.onFailure(it)
                }
            })
        compositeDisposable.add(dispose)
    }

    fun deleteTabungan(nabungData: NabungData) = nabungDao.deleteTabungan(nabungData)

    fun deleteHistoryTabungan(id_tabungan: Int) = nabungDao.deleteHistoryTabungan(id_tabungan)

    fun updateTabungan(
        nama_tabungan: String,
        jumlah_tabungan: Double,
        status: String,
        tenggat_waktu: String,
        desc: String,
        id_tabungan: Int
    ) =
        nabungDao.updateTabungan(
            nama_tabungan = nama_tabungan,
            jumlah_tabungan = jumlah_tabungan,
            tenggat_waktu = tenggat_waktu,
            status = status,
            desc = desc,
            id_tabungan = id_tabungan
        )

    fun updateHistoryTabungan(jumlah_sisa: Int, id_tabungan: Int) =
        nabungDao.updateJumlahSisaTabungan(jumlah_sisa = jumlah_sisa, id_tabungan = id_tabungan)

    fun getAllDataHistoryTabungan() = nabungDao.getHistoryAllTabungan()

    fun insertHistoryTabungan(historyNabung: HistoryNabung) =
        nabungDao.insertHistoryTabungan(historyNabung)

    fun getDataHistoryTabungan(id_tabungan: Int): LiveData<List<HistoryNabung>> =
        nabungDao.getHistoryTabungan(id_tabungan)

}