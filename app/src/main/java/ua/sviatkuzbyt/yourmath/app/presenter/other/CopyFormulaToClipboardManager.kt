package ua.sviatkuzbyt.yourmath.app.presenter.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.sviatkuzbyt.yourmath.app.R
import ua.sviatkuzbyt.yourmath.domain.structures.formula.Formula
import javax.inject.Inject

class CopyFormulaToClipboardManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun copyFormula(data: Formula){
        val text = generateFormulaText(data)
        val clip = ClipData.newPlainText(context.getString(R.string.copied_formula), text)
        clipboardManager.setPrimaryClip(clip)
    }

    private fun generateFormulaText(data: Formula): StringBuilder{
        val text = StringBuilder()
        text.append("${context.getString(R.string.formula)}: ${data.info.label}")

        text.append("\n\n${context.getString(R.string.input_data)}:")
        data.inputData.forEach { input ->
            text.append("\n${input.label} = ${input.data}")
        }

        text.append("\n\n${context.getString(R.string.result)}:")

        data.resultData.forEach { input ->
            text.append("\n${input.label} = ${input.data}")
        }
        return text
    }

    fun showToast(){
        Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show()
    }

    fun copyText(text: String){
        val clip = ClipData.newPlainText(context.getString(R.string.copied_text), text)
        clipboardManager.setPrimaryClip(clip)
    }
}