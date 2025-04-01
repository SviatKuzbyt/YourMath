package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.sviatkuzbyt.yourmath.data.repositories.FormulasRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindFormulasRepository(impl: FormulasRepositoryImpl): FormulasRepository
}