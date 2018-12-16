package com.cats.catsapplication.DI

import android.content.Context
import com.cats.catsapplication.core.utils.AndroidResourceProvider
import com.cats.catsapplication.core.utils.FileProvider
import com.cats.catsapplication.core.utils.ResourceProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule (private val context: Context) {

    @Provides
    fun provideResourceProvider(): ResourceProvider = AndroidResourceProvider(context)

    @Provides
    fun provideFileProvider(): FileProvider = FileProvider(context)
}