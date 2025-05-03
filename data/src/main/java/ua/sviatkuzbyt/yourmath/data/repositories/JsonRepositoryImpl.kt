package ua.sviatkuzbyt.yourmath.data.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import ua.sviatkuzbyt.yourmath.data.other.NoAllDataEnterException
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.edit.export.ExportFormulaItem
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor() : JsonRepository {
    private val fileFormulasType = object : TypeToken<List<ExportFormulaItem>>() {}.type
    private val gson = Gson()

    override fun formulaInputsToJson(inputList: List<FormulaInput>): String {
        val jsonObject = JSONObject()

        inputList.forEach { item ->
            val value = when {
                !item.data.isNullOrBlank() -> item.data
                item.defaultData != null -> item.defaultData
                else -> throw NoAllDataEnterException()
            }

            jsonObject.put(item.codeLabel, value)
        }

        return jsonObject.toString()
    }

    override fun jsonToResultMap(json: String): Map<String, String> {
        val jsonObject = JSONObject(json)
        val mapResults = mutableMapOf<String, String>()

        jsonObject.keys().forEach { key ->
            val value = jsonObject.getString(key)
            mapResults[key] = value
        }

        return mapResults
    }

    override fun fileFormulaItemsToJson(items: List<ExportFormulaItem>): String {
        val jsonString = gson.toJson(items, fileFormulasType)
        return jsonString
    }

    override fun jsonToFileFormulaItems(itemsJson: String): List<ExportFormulaItem> {
        val itemsList: List<ExportFormulaItem> = gson.fromJson(itemsJson, fileFormulasType)
        return itemsList
    }
}