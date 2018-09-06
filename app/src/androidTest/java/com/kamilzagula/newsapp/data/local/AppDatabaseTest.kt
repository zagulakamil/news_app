package com.kamilzagula.newsapp.data.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var newsDao: NewsDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        newsDao = appDatabase.newsDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun test_writeAndRead() {
        val news = NewsEntity(title = "title", description = "desc", publishedAt = null, urlToImage = null, url = "url")
        newsDao.insert(news)
        newsDao.getNewsByTitle("title").test()
                .assertOf {
                    it.assertValue(news)
                }
    }

    @Test
    fun test_getAll() {
        val news = NewsEntity(title = "title", description = "desc", publishedAt = null, urlToImage = null, url = "url")
        val news2 = NewsEntity(title = "title2", description = "desc", publishedAt = null, urlToImage = null, url = "url")
        newsDao.insert(news)
        newsDao.insert(news2)
        newsDao.getAll().flatMapObservable { Observable.fromIterable(it) }
                .test()
                .assertOf {
                    it.assertValueCount(2)
                }
    }

    @Test
    fun test_remove() {
        val news = NewsEntity(title = "title", description = "desc", publishedAt = null, urlToImage = null, url = "url")
        val news2 = NewsEntity(title = "title2", description = "desc", publishedAt = null, urlToImage = null, url = "url")
        newsDao.insert(news)
        newsDao.insert(news2)
        newsDao.remove(news)
        newsDao.getNewsByTitle("title").test()
                .assertErrorMessage("Query returned empty result set: SELECT * FROM newsEntity WHERE title = ? LIMIT 1")
    }
}