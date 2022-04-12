package id.muhariananda.notemarks.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.muhariananda.notemarks.common.Constant.DATABASE_NAME
import id.muhariananda.notemarks.data.db.AppNotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(
            app,
            AppNotesDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideNoteDao(database: AppNotesDatabase) = database.noteDao()

    @Singleton
    @Provides
    fun provideTodoDao(database: AppNotesDatabase) = database.toDoDao()
}