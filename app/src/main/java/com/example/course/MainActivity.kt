@file:Suppress("DEPRECATION")

package com.example.course

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.course.Adapter.FeedAdapter
import com.example.course.Common.HTTPDataHandler
import com.example.course.Model.RSSObject
import com.google.gson.Gson
import android.os.AsyncTask
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val link = "https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml"
    private val RSS_to_Json_API = " https://api.rss2json.com/v1/api.json?rss_url="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarid.title = "Sports News"
        setSupportActionBar(toolbarid)
        val LinearLayoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = LinearLayoutManager
        loadRSS()
    }

    private fun loadRSS() {
        val loadRss = object : AsyncTask<String, String, String>() {
            internal val mdialog = ProgressDialog(this@MainActivity)
            override fun onPreExecute() {
                mdialog.setMessage("Pleas wait...")
                mdialog.show()
            }

            override fun doInBackground(vararg params: String?): String {
                val result: String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])

                return result
            }

            override fun onPostExecute(result: String?) {
                mdialog.dismiss()
                var rssObject: RSSObject
                rssObject = Gson().fromJson(result, RSSObject::class.java!!)
                val adapter = FeedAdapter(rssObject, baseContext)
                recycler_view.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }


            val URL_get_data = StringBuilder(RSS_to_Json_API)
            URL_get_data.append(link)
            loadRss.execute(URL_get_data.toString())
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.main_menu,menu)
            return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_refresh_id)
            loadRSS()
        return true
    }
}