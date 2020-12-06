package com.dwiyanstudio.nabungkuy.module

import android.content.Context
import androidx.room.Room
import com.dwiyanstudio.nabungkuy.Constanst
import com.dwiyanstudio.nabungkuy.database.NabungDatabase
import com.dwiyanstudio.nabungkuy.database.TabunganDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideNabungDatabase(@ApplicationContext context: Context): NabungDatabase {
        return Room.databaseBuilder(context, NabungDatabase::class.java, Constanst.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNabungDao(nabungDatabase: NabungDatabase): TabunganDao {
        return nabungDatabase.nabungDao()
    }
}
