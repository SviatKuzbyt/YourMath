package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.ConvertToPinUnpinFormulaItemsUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.SearchFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase

@Module
@InstallIn(ViewModelComponent::class)
object FormulaModule{
    @Provides
    fun provideGetFormulaUseCase(repository: FormulasRepository): GetFormulaUseCase{
        return GetFormulaUseCase(repository)
    }
}