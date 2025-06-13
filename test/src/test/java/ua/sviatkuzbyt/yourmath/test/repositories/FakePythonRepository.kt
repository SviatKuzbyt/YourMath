package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository

class FakePythonRepository: PythonRepository {

    private val codeResult = "result"

    override fun runCode(code: String, inputJson: String): String {
        return codeResult
    }
}