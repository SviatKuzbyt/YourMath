package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.EditFormulaRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.CreateFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.GetEditFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateFormulaDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateInputDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.editformula.UpdateResultDataUseCase

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
    fun provideUpdateFormulaDataUseCase(
        repository: EditFormulaRepository
    ): UpdateFormulaDataUseCase {
        return UpdateFormulaDataUseCase(repository)
    }

    @Provides
    fun provideUpdateInputDataUseCase(
        repository: EditFormulaRepository
    ): UpdateInputDataUseCase {
        return UpdateInputDataUseCase(repository)
    }

    @Provides
    fun provideUpdateResultDataUseCase(
        repository: EditFormulaRepository
    ): UpdateResultDataUseCase {
        return UpdateResultDataUseCase(repository)
    }

    @Provides
    fun provideCreateFormulaUseCase(
        repository: EditFormulaRepository
    ): CreateFormulaUseCase {
        return CreateFormulaUseCase(repository)
    }
}