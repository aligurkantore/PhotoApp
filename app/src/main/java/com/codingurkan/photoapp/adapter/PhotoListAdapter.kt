package com.codingurkan.photoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codingurkan.photoapp.databinding.PhotoListItemBinding
import com.codingurkan.photoapp.model.Hit
import com.codingurkan.photoapp.utils.loadImage

class PhotoListAdapter(private val photoList : List<Hit>,
private val itemClickListener : ItemClickListener) : RecyclerView.Adapter<PhotoListAdapter.PhotoListViewHolder>() {

    class PhotoListViewHolder(val binding: PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val view = PhotoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoListViewHolder(view)
    }
    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        with(holder.binding){
            val data = photoList[position]
            imageViewList.loadImage(data.previewURL)
            likeTv.text = data.likes.toString()
            commentTv.text = "(${data.comments} comments)"
            viewsTv.text =  "${data.views/(1000)}K(views)"

            imageViewList.setOnClickListener {
                itemClickListener.onClick(data)
            }
        }
    }
    override fun getItemCount(): Int {
        return photoList.size
    }
    interface ItemClickListener{
        fun onClick(data : Hit)
    }
}