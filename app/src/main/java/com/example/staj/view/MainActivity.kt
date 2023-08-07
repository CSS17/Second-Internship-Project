package com.example.staj.view
import EventAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.inflate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    /*
     Bu kod MainViewModel sınıfının bir ornegini olsuturur.
     by lazy işlemi ile ihtiyac duyuldugu zaman olusturulması saglanır.
    */
    private lateinit var recyclerView: RecyclerView

    private val viewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        val eventAdapter = EventAdapter(emptyList()) // Boş liste ile başlatın

        viewModel.matchInfo.observe(this) { matchList ->
            // Adapteri güncelleyin
            eventAdapter.updateData(matchList)
            recyclerView.adapter = eventAdapter
        }

        coroutineScope.launch {
            viewModel.getEventUpdatedData { data, dataList ->
                runOnUiThread {
                    data?.let { eventAdapter.updateData(it) }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.mSocket?.disconnect()
    }


}