package com.kidslearn.bolivia.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Estrellitas guardadas localmente (sin base de datos).
 * Misma API que antes: los fragments no cambian.
 */
object Stars {
    private const val PREFS = "kidslearn_stars"

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    suspend fun add(context: Context, module: String, amount: Int = 1): Int =
        withContext(Dispatchers.IO) {
            val p = prefs(context)
            val updated = p.getInt(module, 0) + amount
            p.edit().putInt(module, updated).apply()
            updated
        }

    suspend fun total(context: Context): Int =
        withContext(Dispatchers.IO) {
            prefs(context).all.values.sumOf { (it as? Int) ?: 0 }
        }
}
