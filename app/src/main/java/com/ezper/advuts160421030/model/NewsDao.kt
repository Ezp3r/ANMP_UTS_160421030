package com.ezper.advuts160421030.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news:News)

    @Query("SELECT * FROM news")
    fun selectAllNews(): List<News>

    @Query("SELECT * FROM news WHERE id= :id")
    fun selectNews(id:Int): News

    @Query("UPDATE news SET judul=:judul, url=:url, deskripsi=:deskripsi, pengarang=:pengarang, detail=:detail WHERE id = :id")
    fun update(judul:String, url:String, deskripsi:String, pengarang:Int, detail:String, id:Int)

    @Delete
    fun deleteNews(news: News)
}