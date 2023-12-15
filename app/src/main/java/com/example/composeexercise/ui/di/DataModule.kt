package com.example.composeexercise.ui.di

import android.content.Context
import androidx.room.Room
import com.example.composeexercise.ui.data.WordDao
import com.example.composeexercise.ui.data.WordRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideWordRoomDatabase(
        @ApplicationContext app: Context
    ): WordRoomDatabase {
        // return instance
        return Room.databaseBuilder(
            app.applicationContext,
            WordRoomDatabase::class.java,
            "word_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWordDao(database: WordRoomDatabase): WordDao {
        return database.wordDao()
    }
}