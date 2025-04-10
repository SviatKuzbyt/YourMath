package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.DeleteFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editor.GetFormulasToEditUseCase

@Module
@InstallIn(ViewModelComponent::class)
object EditorModule{

    @Provides
    fun provideGetFormulasToEditUseCase(
        repository: EditFormulaRepository
    ): GetFormulasToEditUseCase{
        return GetFormulasToEditUseCase(repository)
    }

    @Provides
    fun provideDeleteFormulaUseCase(repository: EditFormulaRepository): DeleteFormulaUseCase{
        return DeleteFormulaUseCase(repository)
    }
}