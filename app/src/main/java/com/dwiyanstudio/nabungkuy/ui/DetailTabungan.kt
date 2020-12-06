package com.dwiyanstudio.nabungkuy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.adapter.HistoryAdapter
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.databinding.DetailSavingsBinding
import com.dwiyanstudio.nabungkuy.helper.ResponseListener
import com.dwiyanstudio.nabungkuy.model.TabunganViewModel
import com.dwiyanstudio.nabungkuy.ui.fragment.BottomSheetTabungan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_savings.*


@AndroidEntryPoint
class DetailTabungan : AppCompatActivity() {
    private val tabunganViewModel: TabunganViewModel by viewModels()
    private lateinit var binding: DetailSavingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.detail_savings)
        val getNabungObject = intent.getParcelableExtra<NabungData>(Constanst.NABUNG_PARCELABLE)
        binding.tabungan = getNabungObject
        getNabungObject?.id?.let {
            tabunganViewModel.getHistoryTabungan(it).observe(this, Observer {
                if (it.isEmpty()) {
                    image_kosong.visibility = View.VISIBLE
                    recycler_history_detail.visibility = View.GONE
                } else {
                    image_kosong.visibility = View.GONE
                    recycler_history_detail.visibility = View.VISIBLE
                    recycler_history_detail.adapter = HistoryAdapter(it)
                    recycler_history_detail.layoutManager = LinearLayoutManager(this)
                }
            })
        }

        edit_detail.setOnClickListener {
            val intentEdit = Intent(this, EditTabungan::class.java)
            intentEdit.putExtra(EditTabungan.PARCELABLE_EXTRA, getNabungObject)
            startActivity(intentEdit)
        }

        add_tabungan_fab.setOnClickListener {
            val bottomSheet = getNabungObject?.let { it1 -> BottomSheetTabungan(it1) }
            bottomSheet?.show(supportFragmentManager, Constanst.BOTTOM_SHEET_TAG)

        }

        delete_detail.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this)

            alertDialogBuilder.setTitle("Hapus Tabungan")
            alertDialogBuilder
                .setMessage("Apakah Anda ingin Menghapus Tabungan Ini ?")
                .setIcon(R.drawable.logo_nabungkuy)
                .setCancelable(false)
                .setPositiveButton(
                    "Ya"
                ) { dialog, id ->
                    if (getNabungObject != null) {
                        tabunganViewModel.deleteTabungan(getNabungObject,
                            object : ResponseListener {
                                override fun onSuccess() {
                                    val intentEdit =
                                        Intent(this@DetailTabungan, MainActivity::class.java)
                                    startActivity(intentEdit)
                                }

                                override fun onFailure(error: String) {
                                    Log.d("ERROR_DB", error)
                                }

                            })
                    }
                }
                .setNegativeButton(
                    "Tidak"
                ) { dialog, id ->
                    dialog.cancel()
                }
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
    }
}