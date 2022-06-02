package kg.iaau.softwarearchitecture.gymtracker.repository

import kg.iaau.softwarearchitecture.gymtracker.data.News
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection
import kg.iaau.softwarearchitecture.gymtracker.db.NewsDao
import kg.iaau.softwarearchitecture.gymtracker.db.TrainingSectionDao
import kg.iaau.softwarearchitecture.gymtracker.db.TrainingDao
import java.util.*
import javax.inject.Inject

class TrainingRepository @Inject constructor(
    private val trainingDao: TrainingDao,
    private val trainingSectionDao: TrainingSectionDao,
    private val newsDao: NewsDao
) {

    suspend fun insertTrainingSections(sections: List<TrainingSection>) = trainingSectionDao.insertAll(sections)

    suspend fun getTrainingSections() = trainingSectionDao.getTrainingSections()

    suspend fun insertTrainings(trainings: List<Training>) = trainingDao.insertAll(trainings)

    suspend fun getTrainings() = trainingDao.getTrainings()

    suspend fun getTraining(id: UUID) = trainingDao.getTraining(id)

    suspend fun updateTraining(training: Training) = trainingDao.updateTraining(training)

    suspend fun insertNews(news: List<News>) = newsDao.insertNews(news)

    suspend fun getAllNews() = newsDao.getAllNews()

    suspend fun getNews(id: UUID) = newsDao.getNews(id)

}