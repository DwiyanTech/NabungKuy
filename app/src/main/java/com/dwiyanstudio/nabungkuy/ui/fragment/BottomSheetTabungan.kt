package com.dwiyanstudio.nabungkuy.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.data.HistoryNabung
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import com.dwiyanstudio.nabungkuy.model.TabunganViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottomsheet_addtabungan.*
import java.time.LocalDateTime

@AndroidEntryPoint
class BottomSheetTabungan(private val nabungData: NabungData) : BottomSheetDialogFragment() {
    private val tabunganViewModel: TabunganViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_addtabungan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabungan_input.addTextChangedListener(CurrencyTextWatcher(tabungan_input))
        tambah_tabungan.setOnClickListener {
            val timeZone = LocalDateTime.now()
            val simpleDateFormat = "${timeZone.year}/${timeZone.monthValue}/${timeZone.dayOfMonth}"
            val jumlahTabungan =
                tabungan_input.text.toString().replace("""[Rp,.]""".toRegex(), "").toDouble()
            val nabungHistoryData = HistoryNabung(
                jumlahTabungan,
                nabungData.id,
                simpleDateFormat,
                nabungData.nama_tabungan
            )

            val operation = nabungData.sisa_tabungan - jumlahTabungan
            tabunganViewModel.insertHistoryTabungan(nabungHistoryData, object : ResponseListener {
                override fun onSuccess() {
                    Snackbar.make(it, Constanst.SUKSES_INSERT_DATA, Snackbar.LENGTH_LONG).show()
                    tabunganViewModel.updateHistoryTabungan(operation.toInt(), nabungData.id)

                }

                override fun onFailure(error: String) {
                    Log.d("ERROR_DB", error)

                }

            })
        }
    }
}