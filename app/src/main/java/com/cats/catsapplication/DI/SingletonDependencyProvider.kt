package com.cats.catsapplication.DI



abstract class SingletonDependencyProvider<S> {

    private var reference: S? = null
    private var provider: (() -> S)? = null

    fun init(provider: () -> S) {
        this.provider = provider
    }

    fun get(): S {
        if (reference == null) {
            reference = provide()
        }
        return reference!!
    }

    fun clear() {
        reference = null
    }

    private fun provide(): S {
        val provided = provider?.invoke()
        if (provided == null) {
            throw IllegalStateException("SingletonDependencyProvider should be initialized")
        } else {
            return provided
        }
    }
}