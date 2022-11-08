package com.example.newdiffutils.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newdiffutils.network.ApiService
import com.example.newdiffutils.network.models.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class APICallingViewModel @Inject constructor(private val retrofitInstance: ApiService) :
    ViewModel() {

    val responseContainer = MutableLiveData<List<Todo>>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>()
    var job : Job? = null
    val exceptionHandler = CoroutineExceptionHandler{_, throwable->
        onError("Exception handled : ${throwable.localizedMessage}")
    }

    fun getTodosFromAPI(){
        isShowProgress.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitInstance.getTodos()
            withContext(Dispatchers.Main){
                if (response.isSuccessful)
                {
                    responseContainer.postValue(response.body())
                    isShowProgress.value = false
                }
                else{
                    onError("Error : ${response.message()}")
                }
            }

        }
    }

    private fun onError(message : String ){
        errorMessage.value = message
        isShowProgress.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}