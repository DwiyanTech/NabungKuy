package com.dwiyanstudio.nabungkuy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiyanstudio.nabungkuy.R
import com.dwiyanstudio.nabungkuy.data.HistoryNabung
import com.dwiyanstudio.nabungkuy.helper.CurrencyConverter
import kotlinx.android.synthetic.main.history_card.view.*

class HistoryAdapter(private val listHistory: List<HistoryNabung>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder>() {
    class HistoryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(historyNabung: HistoryNabung) {
            with(itemView) {
                sisa_tabungan.text =
                    CurrencyConverter.currencyConverter(historyNabung.jumlah_nabung)
                nama_tabungan.text = historyNabung.nama_tabungan
                tgl_pemasukan.text = historyNabung.tanggal_nabung
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_card, parent, false)
        return HistoryAdapterViewHolder(view)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryAdapterViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }
}