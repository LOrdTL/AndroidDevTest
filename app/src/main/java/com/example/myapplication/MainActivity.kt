package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val currencyRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.9262,
        "JPY" to 148.5,
        "GBP" to 0.82
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromCurrencySpinner = findViewById<Spinner>(R.id.fromCurrencySpinner)
        val toCurrencySpinner = findViewById<Spinner>(R.id.toCurrencySpinner)
        val usdAmount = findViewById<EditText>(R.id.usdAmount)
        val eurAmount = findViewById<EditText>(R.id.eurAmount)
        val updateButton = findViewById<Button>(R.id.updateButton)

        // Danh sách loại tiền
        val currencies = currencyRates.keys.toList()

        // Thiết lập Adapter cho Spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromCurrencySpinner.adapter = adapter
        toCurrencySpinner.adapter = adapter

        // Sự kiện cập nhật tỷ giá
        updateButton.setOnClickListener {
            val fromCurrency = fromCurrencySpinner.selectedItem.toString()
            val toCurrency = toCurrencySpinner.selectedItem.toString()
            val amount = usdAmount.text.toString().toDoubleOrNull() ?: 0.0

            // Tính toán tỷ giá giữa hai loại tiền
            val fromRate = currencyRates[fromCurrency] ?: 1.0
            val toRate = currencyRates[toCurrency] ?: 1.0
            val convertedAmount = amount * (toRate / fromRate)

            // Cập nhật kết quả
            eurAmount.setText(convertedAmount.toString())
        }
    }
}




