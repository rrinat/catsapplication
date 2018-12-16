package com.cats.catsapplication.features.favorites.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cats.catsapplication.App
import com.cats.catsapplication.DI.cats.CatsComponent
import com.cats.catsapplication.DI.favorites.FavoritesComponent
import com.cats.catsapplication.R
import com.cats.catsapplication.features.favorites.presentation.fragment.FavoritesFragment
import com.cats.catsapplication.router.Screens

class FavoritesActivity : AppCompatActivity() {

    companion object {

        fun makeIntent(context: Context): Intent = Intent(context, FavoritesActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponents().put(Screens.FAVORITES_SCREEN, this::buildComponent)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, FavoritesFragment.newInstance())
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun buildComponent(): FavoritesComponent {
        return App.getComponents().get<CatsComponent>(Screens.CATS_SCREEN).addFavoritesComponent()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            App.getComponents().remove(Screens.FAVORITES_SCREEN)
        }
    }
}
