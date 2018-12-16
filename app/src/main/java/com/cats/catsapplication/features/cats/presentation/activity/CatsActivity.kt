package com.cats.catsapplication.features.cats.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cats.catsapplication.DI.cats.CatsComponentProvider
import com.cats.catsapplication.R
import com.cats.catsapplication.features.cats.presentation.fragment.CatsFragment

class CatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CatsFragment.newInstance())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            CatsComponentProvider.clear()
        }
    }
}
