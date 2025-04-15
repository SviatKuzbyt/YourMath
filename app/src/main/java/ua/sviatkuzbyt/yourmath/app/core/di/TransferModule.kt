package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ExportUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.transfer.ImportUseCase

@Module
@InstallIn(ViewModelComponent::class)
object TransferModule{
    @Provides
    fun provideExportUseCase(
        editFormulaRepository: EditFormulaRepository,
        jsonRepository: JsonRepository,
        fileRepository: FileRepository
    ): ExportUseCase {
        return ExportUseCase(editFormulaRepository, jsonRepository, fileRepository)
    }

    @Provides
    fun provideImportUseCase(
        editFormulaRepository: EditFormulaRepository,
        jsonRepository: JsonRepository,
        fileRepository: FileRepository
    ): ImportUseCase {
        return ImportUseCase(editFormulaRepository, jsonRepository, fileRepository)
    }
}
