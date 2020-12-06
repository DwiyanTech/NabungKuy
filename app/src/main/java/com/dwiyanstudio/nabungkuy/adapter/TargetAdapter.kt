package com.dwiyanstudio.nabungkuy.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.data.NabungData
import com.dwiyanstudio.nabungkuy.helper.CurrencyConverter
import com.dwiyanstudio.nabungkuy.ui.DetailTabungan
import kotlinx.android.synthetic.main.savings_card.view.*


class TargetAdapter(private val listTarget: List<NabungData>) :
    RecyclerView.Adapter<TargetAdapter.TargetViewHolder>() {
    class TargetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(nabungData: NabungData) {
            with(itemView) {
                tenggat_tabungan.text = "Target :${nabungData.tenggat_waktu}"
                nama_tabungan.text = nabungData.nama_tabungan
                sisa_tabungan.text = CurrencyConverter.currencyConverter(nabungData.sisa_tabungan)
                jumlah_berkala.text = CurrencyConverter.currencyPerMonth(
                    nabungData.sisa_tabungan,
                    nabungData.tenggat_waktu
                )
                itemView.setOnClickListener {
                    val i = Intent(it.context, DetailTabungan::class.java)
                    i.putExtra(Constanst.NABUNG_PARCELABLE, nabungData)
                    it.context.startActivity(i)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TargetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.savings_card, parent, false)
        return TargetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTarget.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TargetAdapter.TargetViewHolder, position: Int) {
        holder.bind(listTarget[position])
    }
}