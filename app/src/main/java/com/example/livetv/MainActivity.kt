package com.example.livetv
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.livetv.adapters.channeladapter
import com.example.livetv.models.channel

class MainActivity : AppCompatActivity() {
    val TAG:String="find123"
    private val channelarray=ArrayList<channel>()
    private lateinit var madapter:channeladapter
    private lateinit var newsadapter:channeladapter
    private lateinit var sportsadapter:channeladapter
    private lateinit var enteradapter:channeladapter
    private val newsarray=ArrayList<channel>()
    private val sportsarray=ArrayList<channel>()
    private val enterarray=ArrayList<channel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView:RecyclerView=findViewById(R.id.recycle)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val url="http://192.168.93.170/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&channels=all"
        channelarray.clear()
        fetchallchannels(url)
       madapter=channeladapter(channelarray,"slider")
       recyclerView.adapter=madapter
        val news_url="http://192.168.93.170/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=News"
        fetchnewschannels(news_url)
        val sports_url="http://192.168.93.170/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Sports"
        fetchsportschannels(sports_url)
        val enter_url="http://192.168.93.170/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Entertainment"
        fetchenterchannels(enter_url)
    }

    private fun fetchenterchannels(url: String) {
        val recyclerView:RecyclerView=findViewById(R.id.enter_channel_list)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        enteradapter=channeladapter(enterarray,"category")
        recyclerView.adapter=enteradapter
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //val channelarray=ArrayList<channel>()
                for(i in 0 until response.length()) {
                    val index: String = i.toString()
                    val jsonobject = response.getJSONObject(index)
                    Log.d(TAG, jsonobject.getString("name"))
                    val news = channel(
                        jsonobject.getInt("id"),
                        jsonobject.getString("name"),
                        jsonobject.getString("description"),
                        jsonobject.getString("live_url"),
                        jsonobject.getString("thumbnail"),
                        jsonobject.getString("facebook"),
                        jsonobject.getString("twitter"),
                        jsonobject.getString("youtube"),
                        jsonobject.getString("website"),
                        jsonobject.getString("category")
                    )
                    enterarray.add(news)
                }
                enteradapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.localizedMessage)
            })
        {}
        queue.add(jsonObjectRequest)
    }

    private fun fetchsportschannels(url: String) {
        val recyclerView:RecyclerView=findViewById(R.id.sports_channel_list)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        sportsadapter=channeladapter(sportsarray,"category")
        recyclerView.adapter=sportsadapter
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //val channelarray=ArrayList<channel>()
                for(i in 0 until response.length()) {
                    val index: String = i.toString()
                    val jsonobject = response.getJSONObject(index)
                    Log.d(TAG, jsonobject.getString("name"))
                    val news = channel(
                        jsonobject.getInt("id"),
                        jsonobject.getString("name"),
                        jsonobject.getString("description"),
                        jsonobject.getString("live_url"),
                        jsonobject.getString("thumbnail"),
                        jsonobject.getString("facebook"),
                        jsonobject.getString("twitter"),
                        jsonobject.getString("youtube"),
                        jsonobject.getString("website"),
                        jsonobject.getString("category")
                    )
                    sportsarray.add(news)
                }
                sportsadapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.localizedMessage)
            })
        {}
        queue.add(jsonObjectRequest)
    }

    private fun fetchnewschannels(url: String) {
         val recyclerView:RecyclerView=findViewById(R.id.news_channel_list)
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        newsadapter=channeladapter(newsarray,"category")
        recyclerView.adapter=newsadapter
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //val channelarray=ArrayList<channel>()
                for(i in 0 until response.length()) {
                    val index: String = i.toString()
                    val jsonobject = response.getJSONObject(index)
                    Log.d(TAG, jsonobject.getString("name"))
                    val news = channel(
                        jsonobject.getInt("id"),
                        jsonobject.getString("name"),
                        jsonobject.getString("description"),
                        jsonobject.getString("live_url"),
                        jsonobject.getString("thumbnail"),
                        jsonobject.getString("facebook"),
                        jsonobject.getString("twitter"),
                        jsonobject.getString("youtube"),
                        jsonobject.getString("website"),
                        jsonobject.getString("category")
                    )
                    newsarray.add(news)
                }
                newsadapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.localizedMessage)
            })
        {}
        queue.add(jsonObjectRequest)
    }

    private fun fetchallchannels(url: String) {
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                //val channelarray=ArrayList<channel>()
                for(i in 0 until response.length()) {
                    val index: String = i.toString()
                    val jsonobject = response.getJSONObject(index)
                    Log.d(TAG, jsonobject.getString("name"))
                    val news = channel(
                        jsonobject.getInt("id"),
                        jsonobject.getString("name"),
                        jsonobject.getString("description"),
                        jsonobject.getString("live_url"),
                        jsonobject.getString("thumbnail"),
                        jsonobject.getString("facebook"),
                        jsonobject.getString("twitter"),
                        jsonobject.getString("youtube"),
                        jsonobject.getString("website"),
                        jsonobject.getString("category")
                    )
                    channelarray.add(news)
                }
                madapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.d(TAG, error.localizedMessage)
            })
        {}
        queue.add(jsonObjectRequest)
    }

}