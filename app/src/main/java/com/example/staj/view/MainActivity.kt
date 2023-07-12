package com.example.staj.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.service.Callback
import com.example.staj.service.RetrofitInstance
import com.example.staj.viewmodel.Matches
import com.example.staj.viewmodel.MyAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Matches>
    private lateinit var retrofitInstance: RetrofitInstance
    private var matches: MutableList<String>? = null
    private var dates: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrayList= arrayListOf<Matches>()
        recyclerView=findViewById(R.id.recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        retrofitInstance=RetrofitInstance()
        retrofitInstance.connectRetrofit(object : Callback {
            override fun onSuccess(match: MutableList<String>,date:MutableList<String>) {
                this@MainActivity.matches = match
                this@MainActivity.dates =date
                Log.d("TAG", matches.toString())
                Log.d("TAG", dates.toString())
                getdata()
            }

            override fun onFailure(message: String) {
                Log.d("TAG", "Başarısız Bağlantı $message")
            }
        })




    }

    private fun getdata() {
        for(i in matches!!.indices){
            val matchInfo=Matches(dates!!.get(i), matches!!.get(i))
            arrayList.add(matchInfo)
        }
        recyclerView.adapter=MyAdapter(arrayList)
    }


}

