package com.example.trainproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainproject.databinding.ActivityMainBinding
import com.example.trainproject.trainSearch.ApiTrainClient
import com.example.trainproject.trainSearch.TrainAdapter
import com.example.trainproject.trainSearch.TrainData
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var mainXml : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        applySavedTheme()
        mainXml = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainXml.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(mainXml.materialToolbar)

        val trainNameEditText = mainXml.trainNameEditText
        val trainNumberEditText = mainXml.trainNumberEditText
        val progressBar = mainXml.progressBarLoadTrainData
        progressBar.visibility = View.INVISIBLE

        mainXml.buttonSearchTrain.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val trainNameSearch = mainXml.trainNameEditText.text
            val mediaType = MediaType.parse("application/json")
            val requestBody = RequestBody.create(mediaType, "{\"search\": \"${trainNameSearch?.trim()}\"}")
            val makeCall = ApiTrainClient.retrofitBuilder.searchTrain(requestBody)
            makeCall.enqueue(object : Callback<List<TrainData>>{
                override fun onResponse(
                    call: Call<List<TrainData>>,
                    response: Response<List<TrainData>>
                ) {
                    val trainList : List<TrainData>? = response.body()
                    Log.i("myTag", "${trainList}")
                    Log.i("myTag", "${response.errorBody()?.string()}")

                    val recyclerView = mainXml.recyclerViewTrainSearchData
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    val myAdapter = TrainAdapter(trainList)
                    recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()


                    if(recyclerView.isDirty){
                        progressBar.visibility = View.INVISIBLE
                    }

                }

                override fun onFailure(call: Call<List<TrainData>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error : ${t.toString()}", Toast.LENGTH_SHORT).show()
                    Log.i("mytag", "${t.toString()}")
                }

            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_light_theme -> {
                setThemeMode(false)
                true
            }
            R.id.menu_dark_theme -> {
                setThemeMode(true)
                true
            }
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> false
        }
    }

    private fun setThemeMode(isDarkTheme: Boolean) {
        val sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("dark_theme", isDarkTheme).apply()

        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        recreate()
    }

    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean("dark_theme", true)

        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}