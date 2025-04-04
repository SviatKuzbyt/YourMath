package ua.sviatkuzbyt.yourmath.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.sviatkuzbyt.yourmath.data.repositories.FormulasRepositoryImpl
import ua.sviatkuzbyt.yourmath.data.repositories.PythonRepositoryImpl
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.repositories.PythonRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindFormulasRepository(impl: FormulasRepositoryImpl): FormulasRepository

    @Binds
    abstract fun bindPythonRepository(impl: PythonRepositoryImpl): PythonRepository
}