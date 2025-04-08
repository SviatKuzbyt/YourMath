package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaWithHistoryDataUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.SaveFormulaToHistoryUseCase

@Module
@InstallIn(ViewModelComponent::class)
object FormulaModule{
    @Provides
    fun provideGetFormulaUseCase(repository: FormulasRepository): GetFormulaUseCase{
        return GetFormulaUseCase(repository)
    }

    @Provides
    fun provideMathFormulaUseCase(
        formulasRepository: FormulasRepository,
        pythonRepository: PythonRepository,
        jsonRepository: JsonRepository
    ): MathFormulaUseCase{
        return MathFormulaUseCase(formulasRepository, pythonRepository, jsonRepository)
    }

    @Provides
    fun provideSaveFormulaToHistoryUseCase(
        repository: HistoryRepository
    ): SaveFormulaToHistoryUseCase {
        return SaveFormulaToHistoryUseCase(repository)
    }

    @Provides
    fun provideGetFormulaWithHistoryDataUseCase(
        formulasRepository: FormulasRepository,
        historyRepository: HistoryRepository
    ): GetFormulaWithHistoryDataUseCase{
        return GetFormulaWithHistoryDataUseCase(formulasRepository, historyRepository)
    }
}