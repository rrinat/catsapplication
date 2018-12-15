package com.cats.catsapplication.DI.cats

import com.cats.catsapplication.features.cats.presentation.fragment.CatsFragment
import com.cats.catsapplication.features.cats.presentation.presentor.CatsPresenter
import dagger.Subcomponent

@CatsScope
@Subcomponent(modules = [CatsModule::class])
interface CatsComponent {

    fun inject(presenter: CatsPresenter)
    fun inject(fragment: CatsFragment)
}