package ua.sviatkuzbyt.yourmath.data.repositories

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.sviatkuzbyt.yourmath.data.other.MathException
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import javax.inject.Inject

class PythonRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PythonRepository {

    override fun runCode(code: String, inputJson: String): String {
        try {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(context))
            }
            val python = Python.getInstance()

            val module = python.getModule("math_code")
            val result = module.callAttr("math_code", code.trimIndent(), inputJson)
            return result.toString()

        } catch (e: Exception){
            throw MathException(e.message)
        }
    }
}