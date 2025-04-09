package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.structures.history.GetFiltersUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.history.CleanHistoryUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.history.GetHistoryUseCase

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule{
    @Provides
    fun provideGetHistoryUseCase(repository: HistoryRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }

    @Provides
    fun provideCleanHistoryUseCase(repository: HistoryRepository): CleanHistoryUseCase {
        return CleanHistoryUseCase(repository)
    }

    @Provides
    fun provideGetFiltersUseCase(repository: FormulasRepository): GetFiltersUseCase {
        return GetFiltersUseCase(repository)
    }
}