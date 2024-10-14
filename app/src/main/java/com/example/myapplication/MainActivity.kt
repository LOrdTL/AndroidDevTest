package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private var <TextView> TextView.text: String
    get() {return text.toString()}
    set(value) {}

class MainActivity<TextView> : AppCompatActivity() {

    private var tvResult: TextView = TODO()
    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Number Buttons
        val buttonNumbers = listOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9)
        )

        // Operation Buttons
        val buttonOperations = listOf<Button>(
            findViewById(R.id.buttonPlus),
            findViewById(R.id.buttonMinus),
            findViewById(R.id.buttonMultiply),
            findViewById(R.id.buttonDivide)
        )

        // Other Buttons
        val buttonEqual = findViewById<Button>(R.id.buttonEqual)
        val buttonClear = findViewById<Button>(R.id.buttonC)
        val buttonBackspace = findViewById<Button>(R.id.buttonBS)
        val buttonDot = findViewById<Button>(R.id.buttonDot)
        val buttonPlusMinus = findViewById<Button>(R.id.buttonPlusMinus)

        buttonNumbers.forEachIndexed { index, button ->
            button.setOnClickListener { appendNumber(index.toString()) }
        }

        buttonOperations.forEach { button ->
            button.setOnClickListener { appendOperation((button as Button).text.toString()) }
        }

        buttonDot.setOnClickListener {
            if (canAddDecimal) {
                appendNumber(".")
                canAddDecimal = false
            }
        }

        buttonEqual.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clearResult() }
        buttonBackspace.setOnClickListener { backspace() }
        buttonPlusMinus.setOnClickListener { togglePlusMinus() }
    }

    private fun appendNumber(number: String) {
        if (tvResult.text == "0") {
            tvResult.text = number
        } else {
            tvResult.text = tvResult.text.toString() + number
        }
        canAddOperation = true
    }

    private fun appendOperation(operation: String) {
        if (canAddOperation) {
            tvResult.text = tvResult.text.toString() + operation
            canAddOperation = false
            canAddDecimal = true
        }
    }

    private fun calculateResult() {
        val expression = tvResult.text.toString()

        try {
            val result = evaluateExpression(expression)
            tvResult.text = result.toString()
        } catch (e: Exception) {
            tvResult.text = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val numbers = mutableListOf<Double>()
        val operations = mutableListOf<Char>()

        var currentNumber = ""
        for (char in expression) {
            when (char) {
                '+', '-', '*', '/' -> {
                    numbers.add(currentNumber.toDouble())
                    operations.add(char)
                    currentNumber = ""
                }
                else -> {
                    currentNumber += char
                }
            }
        }
        numbers.add(currentNumber.toDouble())
        var i = 0
        while (i < operations.size) {
            if (operations[i] == '*' || operations[i] == '/') {
                val result = if (operations[i] == '*') {
                    numbers[i] * numbers[i + 1]
                } else {
                    numbers[i] / numbers[i + 1]
                }
                numbers[i] = result
                numbers.removeAt(i + 1)
                operations.removeAt(i)
            } else {
                i++
            }
        }
        i = 0
        while (i < operations.size) {
            val result = if (operations[i] == '+') {
                numbers[i] + numbers[i + 1]
            } else {
                numbers[i] - numbers[i + 1]
            }
            numbers[i] = result
            numbers.removeAt(i + 1)
            operations.removeAt(i)
        }
        return numbers[0]
    }

    private fun clearResult() {
        tvResult.text = "0"
        canAddOperation = false
        canAddDecimal = true
    }

    private fun backspace() {
        val length = tvResult.text.length
        if (length > 1) {
            tvResult.text = tvResult.text.subSequence(0, length - 1).toString()
        } else {
            tvResult.text = "0"
        }
        canAddOperation = true
    }

    private fun togglePlusMinus() {
        val value = tvResult.text.toString()
        if (value != "0") {
            if (value.startsWith("-")) {
                tvResult.text = value.substring(1)
            } else {
                tvResult.text = "-$value"
            }
        }
    }

}




