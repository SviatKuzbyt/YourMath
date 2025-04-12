package ua.sviatkuzbyt.yourmath.data.repositories

import android.util.Log
import org.json.JSONObject
import ua.sviatkuzbyt.yourmath.data.other.NoAllDataEnterException
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.structures.formula.FormulaInput
import ua.sviatkuzbyt.yourmath.domain.structures.transfer.FileFormulaItem
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor() : JsonRepository {
    override fun formulaInputsToJson(inputList: List<FormulaInput>): String {
        val jsonObject = JSONObject()

        inputList.forEach { item ->
            val value = when {
                item.data.isNotEmpty() -> item.data
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

    override fun fileFormulaItemsToJson(items: List<FileFormulaItem>): String {
        //TODO temp, only for check if correctly selected data from DB
        return items.toString()
    }
}