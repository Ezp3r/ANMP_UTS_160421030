package com.ezper.advuts160421030.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ezper.advuts160421030.model.Berita
import com.ezper.advuts160421030.model.Detail

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val detailLD = MutableLiveData<ArrayList<Detail>>()
    val detailLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue:RequestQueue? = null

    fun fetch(id:String) {
        loadingLD.value = true
        detailLoadErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/news/news_detail.php?news_id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Detail>>() { }.type
                val result = Gson().fromJson<List<Detail>>(it, sType)
                detailLD.value = result as ArrayList<Detail>?
                loadingLD.value = false
                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                detailLoadErrorLD.value = false
                loadingLD.value = false
            })


        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}