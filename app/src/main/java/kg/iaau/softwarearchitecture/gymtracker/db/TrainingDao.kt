package kg.iaau.softwarearchitecture.gymtracker.db

import androidx.room.*
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import java.util.*

@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Training>)

    @Update
    fun updateTraining(training: Training)

    @Query("SELECT * FROM training WHERE id LIKE :id")
    fun getTraining(id: UUID): Training

    @Query("SELECT * FROM training")
    fun getTrainings(): List<Training>

}