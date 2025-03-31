package ua.sviatkuzbyt.yourmath.app.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ua.sviatkuzbyt.yourmath.domain.repositories.FormulasRepository
import ua.sviatkuzbyt.yourmath.domain.usecases.main.GetFormulasUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.PinFormulaUseCase
import ua.sviatkuzbyt.yourmath.domain.usecases.main.UnpinFormulaUseCase

@Module
@InstallIn(ViewModelComponent::class)
object MainModule{
    @Provides
    fun provideGetFormulasUseCase(repository: FormulasRepository): GetFormulasUseCase{
        return GetFormulasUseCase(repository)
    }

    @Provides
    fun providePinFormulaUseCase(repository: FormulasRepository): PinFormulaUseCase {
        return PinFormulaUseCase(repository)
    }

    @Provides
    fun provideUnpinFormulaUseCase(repository: FormulasRepository): UnpinFormulaUseCase {
        return UnpinFormulaUseCase(repository)
    }
}