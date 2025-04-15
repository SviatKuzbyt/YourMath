package ua.sviatkuzbyt.yourmath.domain.repositories

interface FileRepository {
    fun write(fileUri: String, text: String)
    fun read(fileUri: String): String
}