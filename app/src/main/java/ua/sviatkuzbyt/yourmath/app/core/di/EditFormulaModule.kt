package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateTextsUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteAllFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetNewFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.MoveFormulaUseCase

@Module
@InstallIn(ViewModelComponent::class)
object EditFormulaModule{
    @Provides
    fun provideGetEditFormulaDataUseCase(
        repository: EditFormulaRepository
    ): GetEditFormulaDataUseCase {
        return GetEditFormulaDataUseCase(repository)
    }

    @Provides
    fun provideUpdateTextsUseCase(
        repository: EditFormulaRepository
    ): UpdateTextsUseCase {
        return UpdateTextsUseCase(repository)
    }
}