package ua.sviatkuzbyt.yourmath.test.repositories

import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository

class FakeFileRepository: FileRepository {

    var isFileWritten = false
        private set

    val fileData = "Fake file content"

    override fun write(fileUri: String, text: String) {
        isFileWritten = true
    }

    override fun read(fileUri: String): String {
        return fileData
    }
}