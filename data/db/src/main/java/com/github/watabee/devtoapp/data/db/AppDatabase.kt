package com.github.watabee.devtoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.watabee.devtoapp.data.db.daos.FavoriteIchibaItemDao
import com.github.watabee.devtoapp.data.db.entities.FavoriteIchibaItem

@Database(
    entities = [FavoriteIchibaItem::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteIchibaItemDao(): FavoriteIchibaItemDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase.db").build()
    }
}
