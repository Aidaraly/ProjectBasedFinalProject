package kg.iaau.softwarearchitecture.gymtracker.ui.training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.iaau.softwarearchitecture.gymtracker.data.Training
import kg.iaau.softwarearchitecture.gymtracker.data.TrainingSection
import kg.iaau.softwarearchitecture.gymtracker.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(private val repository: TrainingRepository) : ViewModel() {

    val trainingSectionLiveData: LiveData<List<TrainingSection>?>
        get() = _trainingSectionLiveData
    private val _trainingSectionLiveData = MutableLiveData<List<TrainingSection>?>()

    val trainingsLiveData: LiveData<List<Training>?>
        get() = _trainingsLiveData
    private val _trainingsLiveData = MutableLiveData<List<Training>?>()

    val trainingLiveData: LiveData<Training?>
        get() = _trainingLiveData
    private val _trainingLiveData = MutableLiveData<Training?>()

    fun getTrainingSections() {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingSectionLiveData.postValue(repository.getTrainingSections())
        }
    }

    fun getTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingsLiveData.postValue(repository.getTrainings())
        }
    }

    fun getTraining(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingLiveData.postValue(repository.getTraining(id))
        }
    }

    fun updateTraining(training: Training) {
        training.isFavourite = !training.isFavourite
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTraining(training)
        }
        _trainingLiveData.postValue(training)
    }

}