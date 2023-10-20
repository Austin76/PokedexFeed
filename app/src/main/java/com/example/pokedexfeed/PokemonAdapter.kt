package com.example.pokedexfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.Toast

class PokemonAdapter(private val pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView = view.findViewById(R.id.itemPokemonImage)
        val pokemonName: TextView = view.findViewById(R.id.itemPokemonName)
        val pokedexNumber: TextView = view.findViewById(R.id.itemPokedexNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .fitCenter()
            .into(holder.pokemonImage)

        holder.pokemonName.text = "Name: ${pokemon.name}"
        holder.pokedexNumber.text = "Pokédex Number: ${pokemon.id}"

        holder.pokemonImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Pokémon with ID: ${pokemon.id} clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = pokemonList.size
}
