package com.cats.catsapplication.features.favorites.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cats.catsapplication.DI.favorites.FavoritesComponentProvider
import com.cats.catsapplication.R
import com.cats.catsapplication.features.favorites.presentation.fragment.FavoritesFragment

class FavoritesActivity : AppCompatActivity() {

    companion object {

        fun makeIntent(context: Context): Intent = Intent(context, FavoritesActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            FavoritesComponentProvider.clear()
        }
    }
}
