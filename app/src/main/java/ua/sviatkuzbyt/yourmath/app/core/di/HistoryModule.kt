package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.JsonRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.GetHistoryUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.GetFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.MathFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.formula.SaveFormulaToHistoryUseCase

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule{
    @Provides
    fun provideGetHistoryUseCase(repository: HistoryRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }
}