package com.example.staj.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
     /*
      Bu kod MainViewModel sınıfının bir ornegini olsuturur.
      by lazy işlemi ile ihtiyac duyuldugu zaman olusturulması saglanır.
     */
    private val viewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerview)


        viewModel.eventResult.observe(this) {event->
            recyclerView.adapter= EventAdapter(event)

        }

    }




}

