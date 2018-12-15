package com.cats.catsapplication.DI

import java.util.*



object Components {

   private val components = WeakHashMap<String, Any>()

    fun <V> put(key: String, componentBuilder: () -> V) {
        var component = components[key]
        if (component == null) {
            component = componentBuilder()
            components[key] = component
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <V> get(key: String): V {
        return components[key] as V
    }

    fun remove(key: String) {
        components.remove(key)
    }
}