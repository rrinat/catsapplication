package com.cats.catsapplication.DI.cats

import com.cats.catsapplication.DI.SingletonDependencyProvider

class CatsComponentProvider {

    companion object : SingletonDependencyProvider<CatsComponent>()
}