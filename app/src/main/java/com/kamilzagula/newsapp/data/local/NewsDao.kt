package com.kamilzagula.newsapp.data.local

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface NewsDao {
    @Query("SELECT * FROM newsEntity")
    fun getAll(): Single<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsEntity: NewsEntity)

    @Query("SELECT * FROM newsEntity WHERE title = :title LIMIT 1")
    fun getNewsByTitle(title: String): Single<NewsEntity>

    @Delete()
    fun remove(newsEntity: NewsEntity)
}