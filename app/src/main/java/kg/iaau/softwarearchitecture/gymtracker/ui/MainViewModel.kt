package kg.iaau.softwarearchitecture.gymtracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.iaau.softwarearchitecture.gymtracker.data.News
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection
import kg.iaau.softwarearchitecture.gymtracker.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val allNewsLiveData: LiveData<List<News>?>
        get() = _allNewsLiveData
    private val _allNewsLiveData = MutableLiveData<List<News>?>()

    val newsLiveData: LiveData<News?>
        get() = _newsLiveData
    private val _newsLiveData = MutableLiveData<News?>()

    val trainingsLiveData: LiveData<List<Training>?>
        get() = _trainingsLiveData
    private val _trainingsLiveData = MutableLiveData<List<Training>?>()

    fun insertTrainingSections(sections: List<TrainingSection>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getTrainingSections().isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertTrainingSections(sections)
                }
            }
        }
    }

    fun insertTrainings(trainings: List<Training>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getTrainings().isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertTrainings(trainings)
                }
            }
        }
    }

    fun insertNews(news: List<News>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getAllNews().isNullOrEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertNews(news)
                }
            }
        }
    }

    fun getAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _allNewsLiveData.postValue(repository.getAllNews())
        }
    }

    fun getNews(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            _newsLiveData.postValue(repository.getNews(id))
        }
    }

    fun getTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingsLiveData.postValue(repository.getTrainings())
        }
    }

}