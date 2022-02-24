package me.kzaman.android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kzaman.android.database.AppDatabase
import me.kzaman.android.database.dao.CustomerDao
import me.kzaman.android.database.dao.MenuDao
import me.kzaman.android.database.dao.OrderDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "android_mvvm")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideOrderDAO(database: AppDatabase): OrderDao = database.orderDao()

    @Singleton
    @Provides
    fun provideMenuDAO(database: AppDatabase): MenuDao = database.menuDao()

    @Singleton
    @Provides
    fun provideCustomerDAO(database: AppDatabase): CustomerDao = database.customerDao()
}