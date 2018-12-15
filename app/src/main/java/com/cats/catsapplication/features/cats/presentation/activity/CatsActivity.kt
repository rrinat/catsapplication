package com.cats.catsapplication.features.cats.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cats.catsapplication.App
import com.cats.catsapplication.R
import com.cats.catsapplication.features.cats.DI.CatsComponent
import com.cats.catsapplication.features.cats.DI.CatsModule
import com.cats.catsapplication.features.cats.presentation.fragment.CatsFragment
import com.cats.catsapplication.router.Screens

class CatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponents().put(Screens.CATS_SCREEN, this::buildComponent)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CatsFragment.newInstance())
                .commit()
        }
    }

    private fun buildComponent(): CatsComponent {
         return App.getAppComponent().addCatsComponent(CatsModule())
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            App.getComponents().remove(Screens.CATS_SCREEN)
        }
    }
}
