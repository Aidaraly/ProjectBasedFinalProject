package kg.iaau.softwarearchitecture.gymtracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection

@Dao
interface TrainingSectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<TrainingSection>)

    @Query("SELECT * FROM training_category")
    suspend fun getTrainingSections(): List<TrainingSection>

}