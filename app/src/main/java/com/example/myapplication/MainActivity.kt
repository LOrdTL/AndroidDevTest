package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var input1: EditText
    lateinit var input2: EditText
    lateinit var spinner1: Spinner
    lateinit var spinner2: Spinner
    lateinit var exchangeRateText: TextView

    var state: Int = 1
    var op1: Double = 0.0
    var op2: Double = 0.0

    private val exchangeRates = mapOf(
        "Vietnam - Dong" to 25355.0,
        "United States - Dollar" to 1.0,
        "Europe - Euro" to 0.93,
        "Japan - Yen" to 152.3,
        "Korea - Won" to 1388.0
    )

    private val currencyCodes = mapOf(
        "Vietnam - Dong" to "VND",
        "United States - Dollar" to "USD",
        "Europe - Euro" to "EUR",
        "Japan - Yen" to "JPY",
        "Korea - Won" to "KRW"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        exchangeRateText = findViewById(R.id.exchangeRate)

        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)

        findViewById<Button>(R.id.buttonCE).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBS).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDot).setOnClickListener(this)

        // Thiết lập sự kiện thay đổi giá trị cho các Spinner
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        input1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                state = 1
            }
        }

        input2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                state = 2
            }
        }
    }

    override fun onClick(p0: View?) {
        val id = p0?.id
        when (id) {
            R.id.button0 -> addDigit(0)
            R.id.button1 -> addDigit(1)
            R.id.button2 -> addDigit(2)
            R.id.button3 -> addDigit(3)
            R.id.button4 -> addDigit(4)
            R.id.button5 -> addDigit(5)
            R.id.button6 -> addDigit(6)
            R.id.button7 -> addDigit(7)
            R.id.button8 -> addDigit(8)
            R.id.button9 -> addDigit(9)
            R.id.buttonCE -> clearInputs()
        }
    }

    private fun addDigit(c: Int) {
        if (state == 1) {
            op1 = op1 * 10 + c
            input1.setText(op1.toString())
        } else {
            op2 = op2 * 10 + c
            input2.setText(op2.toString())
        }
        convertCurrency()
    }

    private fun clearInputs() {
        op1 = 0.0
        op2 = 0.0
        input1.setText("0")
        input2.setText("0")
    }

    private fun convertCurrency() {
        val currencyFrom = if (state == 1) spinner1.selectedItem.toString() else spinner2.selectedItem.toString()
        val currencyTo = if (state == 1) spinner2.selectedItem.toString() else spinner1.selectedItem.toString()

        val rateFrom = exchangeRates[currencyFrom] ?: 1.0
        val rateTo = exchangeRates[currencyTo] ?: 1.0
        val rate = rateTo / rateFrom

        val codeFrom = currencyCodes[currencyFrom] ?: ""
        val codeTo = currencyCodes[currencyTo] ?: ""

        exchangeRateText.text = "1 $codeFrom = ${String.format("%.6f", rate)} $codeTo"

        if (state == 1) {
            op2 = op1 * rate
            input2.setText(String.format("%.2f", op2))
        } else {
            op1 = op2 * rate
            input1.setText(String.format("%.2f", op1))
        }
    }
}