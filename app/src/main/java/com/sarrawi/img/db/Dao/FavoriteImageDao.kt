package com.sarrawi.img.db.Dao

import androidx.room.*
import com.sarrawi.img.model.FavoriteImage

@Dao
interface FavoriteImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteImage(favoriteImage: FavoriteImage)

    @Delete
    suspend fun deleteFavoriteImage(favoriteImage: FavoriteImage)

    @Query("SELECT * FROM favorite_images")
    suspend fun getAllFavoriteImages(): List<FavoriteImage>
}