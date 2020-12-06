package com.dwiyanstudio.nabungkuy.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.data.TabunganRepository
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import com.dwiyanstudio.nabungkuy.helper.TimeConverter
import com.dwiyanstudio.nabungkuy.ui.fragment.CurrencyTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.tabungan_input.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddTabungan : AppCompatActivity() {
    @Inject
    lateinit var nabungRepository: TabunganRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tabungan_input)
        toolbar.title = "Tambah Tabungan"
        setSupportActionBar(toolbar)
        init()

    }

    fun init() {

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

        toolbar.setNavigationOnClickListener {
            val intent = Intent(
                this@AddTabungan,
                MainActivity::class.java
            )
            startActivity(intent)
        }
        harga_barang.addTextChangedListener(CurrencyTextWatcher(harga_barang))
        tambah_tabungan.setOnClickListener {
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
                val time_long = TimeConverter.convertTimeToLong(time_target)
                val dataTabungan = NabungData(
                    name,
                    price,
                    time_target,
                    time_long,
                    "false",
                    desc,
                    price
                )
                nabungRepository.insertTabungan(dataTabungan, object : ResponseListener {
                    override fun onSuccess() {
                        val intent = Intent(
                            this@AddTabungan,
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

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }


}