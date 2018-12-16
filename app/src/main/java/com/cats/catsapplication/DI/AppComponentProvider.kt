package com.cats.catsapplication.DI

interface AppComponentProvider {

    companion object : SingletonDependencyProvider<AppComponent>()
}