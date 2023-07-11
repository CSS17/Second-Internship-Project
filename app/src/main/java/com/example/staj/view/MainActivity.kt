package com.example.staj.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.staj.R
import com.example.staj.models.Data
import com.example.staj.models.Event
import com.example.staj.service.RetrofitApi
import com.example.staj.service.RetrofitInstance
import com.example.staj.utils.Constants.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getEvents()
    }
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getEvents() {
        val retrofit = createRetrofit()
        val eventService = retrofit.create(RetrofitApi::class.java)

        val call = eventService.getEvents()
        call.enqueue(object : retrofit2.Callback<Event> {
            override fun onResponse(call: Call<Event>, response: retrofit2.Response<Event>) {
                if (response.isSuccessful) {
                    val events = response.body()
                    Log.d("TAG",events.toString())
                    // Verileri burada kullanabilirsiniz
                } else {
                    Log.d("TAG","API yanıtında hata var.")
                    // API yanıtında bir hata var
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.d("TAG","Başarısız Bağlantı ${call} ${t.message}")
            }
        })
    }

}