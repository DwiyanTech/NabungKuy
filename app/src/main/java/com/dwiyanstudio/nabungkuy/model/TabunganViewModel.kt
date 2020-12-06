package com.dwiyanstudio.nabungkuy.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.dwiyanstudio.nabungkuy.data.HistoryNabung
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.data.TabunganRepository
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class TabunganViewModel @ViewModelInject constructor(private val tabunganRepository: TabunganRepository) :
    ViewModel() {
    val getAllTabunganData = tabunganRepository.getDataTabungan()
    val compositeDisposable = CompositeDisposable()
    val getAllTabunganHistory = tabunganRepository.getAllDataHistoryTabungan()
 val getjumlahAllTabungan = tabunganRepository.getJumlahSumTabungan
    fun updateTabungan(
        nama_tabungan: String,
        jumlah_tabungan: Double,
        status: String,
        tenggat_waktu: String,
        desc: String,
        id_tabungan: Int,
        responseListener: ResponseListener
    ) {
        val dispose =
            tabunganRepository.updateTabungan(
                nama_tabungan = nama_tabungan,
                jumlah_tabungan = jumlah_tabungan,
                desc = desc,
                tenggat_waktu = tenggat_waktu,
                id_tabungan = id_tabungan,
                status = status
            )
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseListener.onSuccess() }, { error ->
                    error.message?.let {
                        responseListener.onFailure(it)
                    }
                })
        compositeDisposable.add(dispose)
    }


    fun deleteTabungan(nabungData: NabungData, responseListener: ResponseListener) =
        tabunganRepository.deleteTabungan(nabungData)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                tabunganRepository.deleteHistoryTabungan(nabungData.id)
                    .subscribeOn(Schedulers.computation()).subscribe()
            }
            .subscribeOn(Schedulers.computation())
            .subscribe({ tes ->
                responseListener.onSuccess()
            }, { error ->
                error.message?.let {
                    responseListener.onFailure(
                        it
                    )
                }
            })


    fun getHistoryTabungan(id_tabungan: Int) =
        tabunganRepository.getDataHistoryTabungan(id_tabungan)

    fun updateHistoryTabungan(jumlah_tabungan: Int, id_tabungan: Int) {
        val dispose = tabunganRepository.updateHistoryTabungan(jumlah_tabungan, id_tabungan)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe()
        compositeDisposable.add(dispose)
    }

    fun insertHistoryTabungan(nabung: HistoryNabung, responseListener: ResponseListener) {
        val dispose = tabunganRepository.insertHistoryTabungan(nabung)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success -> responseListener.onSuccess() }, { error ->
                error.message?.let {
                    responseListener.onFailure(it)
                }
            })
        compositeDisposable.add(dispose)
    }
}