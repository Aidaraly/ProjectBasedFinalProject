package kg.iaau.softwarearchitecture.gymtracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.iaau.softwarearchitecture.gymtracker.data.News
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection

@Database(entities = [Training::class, TrainingSection::class, News::class], version=1, exportSchema = false)
@TypeConverters(GymTypeConverter::class)
abstract class GymDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao

    abstract fun trainingCategoryDao(): TrainingSectionDao

    abstract fun newsDao(): NewsDao

}