package ua.sviatkuzbyt.yourmath.domain.usecases.transfer

import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository

class ImportUseCase(
    private val editFormulaRepository: EditFormulaRepository,
    private val jsonRepository: JsonRepository,
    private val fileRepository: FileRepository
) {
    fun execute(fileUri: String){
        println("SKLT formulas from $fileUri was imported")
    }
}