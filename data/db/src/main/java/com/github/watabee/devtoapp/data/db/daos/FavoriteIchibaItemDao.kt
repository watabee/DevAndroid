package com.github.watabee.devtoapp.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.watabee.devtoapp.data.db.entities.FavoriteIchibaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteIchibaItemDao {

    @Query("SELECT itemCode FROM favorite_ichiba_items")
    fun getItemCodes(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: FavoriteIchibaItem)

    @Query("DELETE FROM favorite_ichiba_items WHERE itemCode = :itemCode")
    suspend fun delete(itemCode: String)
}
