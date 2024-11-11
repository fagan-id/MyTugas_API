package com.example.mytugas_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytugas_api.databinding.ItemPictureBinding
import com.example.mytugas_api.model.Dogs


typealias OnClickData = (String) -> Unit
class DataAdapter(
    private var listDogs : List<String>,
    private val OnClickData : OnClickData
) : RecyclerView.Adapter<DataAdapter.ItemDataViewHolder>(){

    inner class ItemDataViewHolder(private val binding : ItemPictureBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(imageUrl : String){
                    with(binding){

                        Glide.with(itemView.context).load(imageUrl).into(imgView)

                        itemView.setOnClickListener{
                            OnClickData(imageUrl)
                        }
                    }
                }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDataViewHolder {
        val binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDogs.size
    }

    override fun onBindViewHolder(holder: ItemDataViewHolder, position: Int) {
        holder.bind(listDogs[position])
    }
}