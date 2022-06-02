package kg.iaau.softwarearchitecture.gymtracker.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.iaau.softwarearchitecture.gymtracker.db.GymDatabase
import kg.iaau.softwarearchitecture.gymtracker.utils.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): GymDatabase {
        return Room.databaseBuilder(
            app,
            GymDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTrainingDao(db: GymDatabase) = db.trainingDao()

    @Provides
    @Singleton
    fun provideTrainingCategoryDao(db: GymDatabase) = db.trainingCategoryDao()

    @Provides
    @Singleton
    fun provideNewsDao(db: GymDatabase) = db.newsDao()

}