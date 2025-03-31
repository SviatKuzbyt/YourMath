package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import ua.sviatkuzbyt.yourmath.data.fake.FormulasRepositoryFakeImpl
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase

@Module
@InstallIn(ViewModelComponent::class)
object FormulasModule{
    @Provides
    fun provideGetFormulasUseCase(repository: FormulasRepository): GetFormulasUseCase{
        return GetFormulasUseCase(repository)
    }
}