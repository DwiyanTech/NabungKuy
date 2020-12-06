package com.dwiyanstudio.nabungkuy.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.adapter.HistoryAdapter
import com.dwiyanstudio.nabungkuy.adapter.TargetAdapter
import com.dwiyanstudio.nabungkuy.helper.CurrencyConverter
import com.dwiyanstudio.nabungkuy.model.TabunganViewModel
import com.dwiyanstudio.nabungkuy.service.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.notification_settings.view.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val tabunganViewModel: TabunganViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        pengaturan_notifikasi.setOnClickListener {
            alertDialog()
        }

    }

    private fun init() {
        tabunganViewModel.getAllTabunganHistory.observe(this, Observer { response ->
            if (response.isEmpty()) {
                recycler_history.visibility = View.GONE
                gambar_kosong_history.visibility = View.VISIBLE
            } else {
                gambar_kosong_history.visibility = View.GONE
                recycler_history.visibility = View.VISIBLE
                recycler_history.adapter = HistoryAdapter(response)
                recycler_history.layoutManager = LinearLayoutManager(this)
            }
        })

        tabunganViewModel.getjumlahAllTabungan.observe(this, Observer {response ->
            sisa_tabungan.text = CurrencyConverter.currencyConverter(response.total_tabungan)

        })
        tabunganViewModel.getAllTabunganData.observe(this, Observer { response ->
            if (response.isEmpty()) {
                sisa_tabungan.text = "0"
                gambar_kosong.visibility = View.VISIBLE
                recycler_target.visibility = View.GONE
            } else {

                gambar_kosong.visibility = View.GONE
                recycler_target.visibility = View.VISIBLE
                recycler_target.layoutManager = LinearLayoutManager(this)
                recycler_target.adapter = TargetAdapter(response)
            }
        })

        add_tabungan.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                AddTabungan::class.java
            )
            startActivity(intent)
        }

    }

    private fun alertDialog() {
        val prefs = getSharedPreferences(Constanst.PREFS_NAME_SETTINGS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.notification_settings, null)
        dialog.setView(view)
        val switch = view.switch_notifikasi
        val bool_prefs = prefs.getBoolean(Constanst.BOOLEAN_SETTINGS, false)
        switch.isChecked = bool_prefs
        dialog.setCancelable(true)
        dialog.setPositiveButton("SIMPAN")
        { dialogInterface, i ->
            editor.putBoolean(Constanst.BOOLEAN_SETTINGS, switch.isChecked)
            editor.apply()
            val alarmReceiver = AlarmReceiver()
            if (switch.isChecked) {
                alarmReceiver.showRepeatingAlarm(this)
            } else {
                alarmReceiver.cancelAlarm(this)
            }
        }
        dialog.show()
    }


}
