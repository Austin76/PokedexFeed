package com.example.pokedexfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import okhttp3.Headers
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val pokemonList = mutableListOf<Pokemon>()
    private lateinit var pokemonRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private val pokemonAdapter = PokemonAdapter(pokemonList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonRecyclerView = findViewById(R.id.pokemonRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)

        // Set the adapter and layout manager here, so you don't keep resetting it every time you fetch a Pokemon
        pokemonRecyclerView.adapter = pokemonAdapter
        pokemonRecyclerView.layoutManager = LinearLayoutManager(this)
        pokemonRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        fetchTwoPokemon()

        searchButton.setOnClickListener {
            searchPokemon()
        }
    }

    private fun fetchTwoPokemon() {
        val randomTwoIds = (1..1017).shuffled().take(2)
        for (id in randomTwoIds) {
            val apiURL = "https://pokeapi.co/api/v2/pokemon/$id/"
            fetchPokemon(apiURL)
        }
    }

    private fun fetchPokemon(apiURL: String) {
        val client = AsyncHttpClient()

        client[apiURL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                val pokemonName = json.jsonObject.getString("name")
                val pokemonNumber = json.jsonObject.getInt("id")

                val sprites = json.jsonObject.getJSONObject("sprites")
                val imageUrl = sprites.getString("front_default")

                pokemonList.add(Pokemon(pokemonNumber, pokemonName, imageUrl))
                pokemonAdapter.notifyItemInserted(pokemonList.size - 1)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }

    private fun searchPokemon() {
        val pokemonNameOrId = searchEditText.text.toString().trim()

        if (pokemonNameOrId.isEmpty()) {
            return
        }

        val apiURL = "https://pokeapi.co/api/v2/pokemon/$pokemonNameOrId/"
        fetchPokemon(apiURL)
    }
}
