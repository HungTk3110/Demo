package ccom.example.demo.data.di.Module

import android.app.Application
import androidx.room.Room

import com.example.demo.data.room.CharacterDao
import com.example.demo.data.room.DataBase
import com.example.demo.data.room.RemoteKeyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule {
    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): DataBase {
        return Room.databaseBuilder(
            application, DataBase::class.java, "DataBase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCharacterDao(appDatabase: DataBase): CharacterDao {
        return appDatabase.getCharacterDao()
    }
    @Provides
    @Singleton
    internal fun provideRemoteKeyDao(appDatabase: DataBase): RemoteKeyDao {
        return appDatabase.getRemoteKeyDao()
    }
}