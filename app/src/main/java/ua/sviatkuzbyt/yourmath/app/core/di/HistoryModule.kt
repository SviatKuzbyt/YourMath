package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.HistoryRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.GetHistoryUseCase

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule{
    @Provides
    fun provideGetHistoryUseCase(repository: HistoryRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }
}