package com.dwiyanstudio.nabungkuy.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.databinding.EditTabunganInputBinding
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import com.dwiyanstudio.nabungkuy.model.TabunganViewModel
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.edit_tabungan_input.*
import java.util.*

@AndroidEntryPoint
class EditTabungan : AppCompatActivity() {
    private lateinit var editTabunganInputBinding: EditTabunganInputBinding
    private val tabunganViewModel: TabunganViewModel by viewModels()

    companion object {
        const val PARCELABLE_EXTRA = "EXTRA_PARC"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTabunganInputBinding =
            DataBindingUtil.setContentView(this, R.layout.edit_tabungan_input)
        val getNabungObject = intent.getParcelableExtra<NabungData>(PARCELABLE_EXTRA)
        editTabunganInputBinding.tabungan = getNabungObject
        init()
    }

    private fun init() {
        val getNabungObject = intent.getParcelableExtra<NabungData>(PARCELABLE_EXTRA)
        target_waktu.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    target_waktu.setText("${year}/${monthOfYear + 1}/${dayOfMonth}")
                },
                year,
                month,
                day
            )
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
        }
        edit_tabungan_btn.setOnClickListener {

            val name = nama_barang.text.toString()
            val priceString = harga_barang.text.toString()
            val price =
                if (priceString.isNotEmpty()) priceString.replace("""[Rp,.]""".toRegex(), "")
                    .toDouble() else 0.0
            val time_target = target_waktu.text.toString()
            val desc = deskripsi.text.toString()
            if (name.isEmpty() || priceString.isEmpty() || time_target.isEmpty() || desc.isEmpty()) {
                if (name.isEmpty()) {
                    nama_barang.error = Constanst.ERROR_ISEMPTY
                }

                if (priceString.isEmpty()) {
                    harga_barang.error = Constanst.ERROR_ISEMPTY
                }

                if (time_target.isEmpty()) {
                    target_waktu.error = Constanst.ERROR_ISEMPTY
                }

                if (desc.isEmpty()) {
                    deskripsi.error = Constanst.ERROR_ISEMPTY
                }
            } else {
                tabunganViewModel
                    .updateTabungan(nama_tabungan = name,
                        jumlah_tabungan = price,
                        status = getNabungObject!!.status,
                        tenggat_waktu = time_target,
                        desc = desc,
                        id_tabungan = getNabungObject.id,
                        responseListener = object : ResponseListener {
                            override fun onSuccess() {
                                val intent = Intent(
                                    this@EditTabungan,
                                    MainActivity::class.java
                                )
                                startActivity(intent)
                            }

                            override fun onFailure(error: String) {
                                Log.d("ERROR_DB", error)
                            }

                        })
            }

        }
    }
}