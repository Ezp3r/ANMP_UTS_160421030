package com.ezper.advuts160421030.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp.util.buildDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ezper.advuts160421030.model.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val newsLD = MutableLiveData<List<News>>()
    val newsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        newsLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())

            newsLD.postValue(db.newsDao().selectAllNews())
            loadingLD.postValue(false)
        }
    }



}