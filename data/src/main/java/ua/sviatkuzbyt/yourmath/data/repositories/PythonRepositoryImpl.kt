package ua.sviatkuzbyt.yourmath.data.repositories

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import ua.sviatkuzbyt.yourmath.data.MathException
import ua.sviatkuzbyt.yourmath.data.NoAllDataEnterException
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.InputDataFormula
import ua.sviatkuzbyt.yourmath.domain.structures.formula.ResultDataFormula
import javax.inject.Inject

class PythonRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PythonRepository {
    override fun convertInputDataToJSON(input: List<InputDataFormula>): String {
        val jsonObject = JSONObject()

        input.forEach { item ->
            val value = when {
                item.data.isNotEmpty() -> item.data
                item.defaultData != null -> item.defaultData
                else -> throw NoAllDataEnterException()
            }

            jsonObject.put(item.codeLabel, value)
        }

        return jsonObject.toString()
    }

    override fun runCode(code: String, inputData: String): String {
        try {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(context))
            }
            val python = Python.getInstance()

            println("SKLT $inputData")

            val module = python.getModule("math_code")
            val result = module.callAttr("math_code", code.trimIndent(), inputData)
            return result.toString()
        } catch (e: Exception){
            println("SKLT $e || ${e.message}")
            throw MathException(e.message)
        }
    }

    override fun putJSONToResultData(
        resultJSON: String,
        resultList: List<ResultDataFormula>
    ): List<ResultDataFormula> {
        val jsonObject = JSONObject(resultJSON)
        val jsonResults = mutableMapOf<String, String>()

        jsonObject.keys().forEach { key ->
            val value = jsonObject.getString(key)
            jsonResults[key] = value
        }

        return resultList.map { data ->
            data.copy(
                data = jsonResults[data.codeLabel]
            )
        }
    }
}