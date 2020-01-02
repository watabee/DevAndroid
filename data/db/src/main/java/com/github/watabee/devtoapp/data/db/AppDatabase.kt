package com.github.watabee.devtoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.watabee.devtoapp.data.db.daos.ArticleDao
import com.github.watabee.devtoapp.data.db.entities.ArticleDetailEntity

@Database(
    entities = [ArticleDetailEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase.db").build()
    }
}
