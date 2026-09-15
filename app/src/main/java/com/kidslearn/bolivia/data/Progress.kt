package com.kidslearn.bolivia.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey val module: String,
    val stars: Int
)

@Dao
interface ProgressDao {
    @Query("SELECT stars FROM progress WHERE module = :module")
    suspend fun getStars(module: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(progress: Progress)

    @Query("SELECT COALESCE(SUM(stars),0) FROM progress")
    suspend fun totalStars(): Int
}

@Database(entities = [Progress::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "kidslearn.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}

object Stars {
    suspend fun add(context: Context, module: String, amount: Int = 1): Int {
        val db = AppDatabase.get(context)
        val current = db.progressDao().getStars(module) ?: 0
        val updated = current + amount
        db.progressDao().save(Progress(module, updated))
        return updated
    }
    suspend fun total(context: Context): Int =
        AppDatabase.get(context).progressDao().totalStars()
}
