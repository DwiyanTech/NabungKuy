package com.dwiyanstudio.nabungkuy.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.*

class CurrencyTextWatcher(private val textInput: TextInputEditText) : TextWatcher {
    override fun afterTextChanged(text: Editable?) {
        var current = ""
        var formatRupiah = ""
        var replesString = ""
        if (!text.isNullOrEmpty()) {
            textInput.removeTextChangedListener(this)
            try {
                replesString = text.replace("""[Rp,.]""".toRegex(), "")
            } catch (e: Exception) {
                replesString = "0.0"
            }
            val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            format.maximumFractionDigits = 0
            format.isParseIntegerOnly = true

            if (replesString.isNotEmpty()) {
                val decimal = replesString.toDouble()
                formatRupiah = format.format(decimal)
            } else {
                val decimal = 0.0
                formatRupiah = format.format(decimal)

            }
            current = formatRupiah
            textInput.setText(current)
            textInput.setSelection(current.length)
            textInput.addTextChangedListener(this)
        }


    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}