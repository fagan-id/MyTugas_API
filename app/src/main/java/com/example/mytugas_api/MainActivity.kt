package com.example.mytugas_api

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytugas_api.databinding.ActivityMainBinding
import com.example.mytugas_api.model.Dogs
import com.example.mytugas_api.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val dogList = ArrayList<String>() // List to store image URLs
    private lateinit var adapter: DataAdapter // Custom adapter for RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        fetchDogImages(20) // Fetch 10 random dog images
    }

    private fun setupRecyclerView() {
        adapter = DataAdapter(dogList) { imageUrl ->
            Toast.makeText(this@MainActivity, "You Clicked On: $imageUrl", Toast.LENGTH_SHORT).show()
        }

        binding.rvData.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
    }

    private fun fetchDogImages(count: Int) {
        val client = ApiClient.getInstance()

        repeat(count) {
            client.getAllDogs().enqueue(object : Callback<Dogs> {
                override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {
                    if (response.isSuccessful && response.body() != null) {
                        // Add each image URL to the list and notify the adapter
                        val dogs = response.body()
                        dogs?.picture?.let {
                            dogList.add(it)
                            adapter.notifyItemInserted(dogList.size - 1)
                        }
                    } else {
                        Log.e("MainActivity", "Response failed: ${response.errorBody()?.string()}")
                        Toast.makeText(this@MainActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Dogs>, t: Throwable) {
                    Log.e("MainActivity", "Network request failed: ${t.message}")
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
