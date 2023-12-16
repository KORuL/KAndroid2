package ru.korul.kandroid2

import android.app.Service
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

/**
 * Делегат для получения значений по ключу из [Intent]
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] интента нет значения с указанным ключем будет использовано [defaultValue]
 * если [defaultValue] == null будет выброшено исключение
 *
 * @return значение [T] не может быть null
 */
inline fun <reified T> AppCompatActivity.argNotNull(
    key: String? = null,
    defaultValue: T? = null
): LazyProvider<AppCompatActivity, T> {
    return argDelegate(key, defaultValue) { it.intent?.extras }
}

/**
 * Делегат для получения значений по ключу из arguments
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] аргументов нет значения с указанным ключем будет использовано [defaultValue]
 * если [defaultValue] == null будет выброшено исключение
 *
 * @return значение [T] не может быть null
 */
inline fun <reified T> Fragment.argNotNull(
    key: String? = null,
    defaultValue: T? = null
): LazyProvider<Fragment, T> {
    return argDelegate(key, defaultValue) { it.arguments }
}

/**
 * Делегат для получения значений по ключу из [Intent]
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] интента нет значения с указанным ключем будет использовано [defaultValue]
 * если [defaultValue] == null будет выброшено исключение
 *
 * @return значение [T] не может быть null
 */
inline fun <reified T> Service.argNotNull(
    key: String? = null,
    defaultValue: T? = null
): LazyProvider<AppCompatActivity, T> {
    return argDelegate(key, defaultValue) { it.intent?.extras }
}

/**
 * Делегат для получения значений по ключу из [Intent]
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] интента нет значения с указанным ключем будет возвращен null
 *
 * @return значение [T] может быть null
 */
inline fun <reified T> AppCompatActivity.arg(
    key: String? = null
): LazyProvider<AppCompatActivity, T?> {
    return nullableArgDelegate(key) { it.intent?.extras }
}

/**
 * Делегат для получения значений по ключу из arguments
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] аргументов нет значения с указанным ключем будет возвращен null
 *
 * @return значение [T] может быть null
 */
inline fun <reified T> Fragment.arg(
    key: String? = null
): LazyProvider<Fragment, T?> {
    return nullableArgDelegate(key) { it.arguments }
}

/**
 * Делегат для получения значений по ключу из [Intent]
 * если [key] == null в качестве ключа берется имя переменной,
 * если в [Bundle] интента нет значения с указанным ключем будет возвращен null
 *
 * @return значение [T] может быть null
 */
inline fun <reified T> Service.arg(
    key: String? = null
): LazyProvider<AppCompatActivity, T?> {
    return nullableArgDelegate(key) { it.intent?.extras }
}

inline fun <F, reified T> nullableArgDelegate(
    key: String?,
    crossinline provideArguments: (F) -> Bundle?
): LazyProvider<F, T?> = object : LazyProvider<F, T?> {
    override fun provideDelegate(thisRef: F, prop: KProperty<*>) = lazy {
        val bundle = provideArguments(thisRef)
        @Suppress("DEPRECATION")
        bundle?.get(key ?: prop.name) as T?
    }
}

inline fun <F, reified T> argDelegate(
    key: String?,
    defaultValue: T? = null,
    crossinline provideArguments: (F) -> Bundle?
): LazyProvider<F, T> = object : LazyProvider<F, T> {
    override fun provideDelegate(thisRef: F, prop: KProperty<*>) = lazy {
        val bundle = provideArguments(thisRef)
        @Suppress("DEPRECATION")
        (bundle?.get(key ?: prop.name) as? T) ?: defaultValue ?: error("Value ${prop.name} null")
    }
}

interface LazyProvider<A, T> {
    operator fun provideDelegate(thisRef: A, prop: KProperty<*>): Lazy<T>
}
