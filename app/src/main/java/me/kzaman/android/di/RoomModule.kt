package me.kzaman.android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kzaman.android.database.AppDatabase
import me.kzaman.android.database.dao.ChildMenuDao
import me.kzaman.android.database.dao.ParentMenuDao
import me.kzaman.android.database.dao.ProductDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideProductDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "android_mvvm")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDAO(database: AppDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideParentMenuDAO(database: AppDatabase): ParentMenuDao = database.parentMenuDao()

    @Singleton
    @Provides
    fun provideChildMenuDAO(database: AppDatabase): ChildMenuDao = database.childMenuDao()
}