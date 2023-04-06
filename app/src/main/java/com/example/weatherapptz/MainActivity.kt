package com.example.weatherapptz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapptz.databinding.ActivityMainBinding
import org.json.JSONObject
import kotlin.math.roundToInt

const val API_KEY = "9f6ea3e545ae1a1822ef1500e53854da"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        binding.getWeather.setOnClickListener {
            binding.textTemp.visibility = View.VISIBLE
            binding.textFeels.visibility = View.VISIBLE
            binding.textWeather.visibility = View.VISIBLE
            getResult(binding)
        }
    }

    private fun getResult(binding: ActivityMainBinding) {
        val city = binding.setCityName.text
        val url =
            "https://api.openweathermap.org/data/2.5/weather?&q=$city&units=metric&appid=$API_KEY"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                binding.errorShow.visibility = View.GONE
                binding.cityName.text = city
                binding.currentTemperature.text =
                    "${obj.getJSONObject("main").get("temp").toString().toDouble().roundToInt()} °C"
                binding.feelTemp.text =
                    "${obj.getJSONObject("main").get("feels_like").toString().toDouble().roundToInt()} °C"
                binding.currentWeather.text =
                    "${obj.getJSONArray("weather").getJSONObject(0).get("main")}"

            },
            {
                binding.errorShow.visibility = View.VISIBLE
                binding.errorShow.text = it.message
            }
        )
        queue.add(request)
    }
}