package com.example.staj.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.models.EventItem
import com.example.staj.view.model.ViewItem
import com.example.staj.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
     /*
      Bu kod MainViewModel sınıfının bir ornegini olsuturur.
      by lazy işlemi ile ihtiyac duyuldugu zaman olusturulması saglanır.
     */
    private var adapterSetFlag:Boolean = false
    private lateinit var recyclerView: RecyclerView
    private val eventList = mutableListOf<EventItem>()
    private val ratioList = mutableListOf<ViewItem>()
    private val viewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerview)


        viewModel.eventResult.observe(this) {event->
            eventList.clear()
            eventList.addAll(event)
            Log.d("TAG","1.KISIM")


            //recyclerView.adapter= EventAdapter(event)
        }
        viewModel.ratioResult.observe(this){ratio->
              ratioList.clear()
              ratioList.addAll(ratio)
              Log.d("TAG","${ratioList}")
                adapterSetFlag = true // Flag'i true olarak işaretle
                viewModel.checkAdapterSet(adapterSetFlag,recyclerView,eventList,ratioList)

        }




    }




}

