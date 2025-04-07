package ua.sviatkuzbyt.yourmath.domain.repositories

interface PythonRepository {
    fun runCode(code: String, inputJson: String): String
}