package com.hariharan.aboutcanada.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hariharan.aboutcanada.R
import com.hariharan.aboutcanada.data.model.ItemInfo

/**
 * Adapter class to show the items in the recycler view.
 */
class FactsAdapter(private var list: List<ItemInfo>) :
    RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val factInfo = list[position]
        if (factInfo.imageUrl !== null) {
            Glide.with(context)
                .load(factInfo.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView)
        } else {
            Glide.with(context).clear(holder.imageView)
            holder.imageView.setImageDrawable(null)
        }
        holder.name.text = factInfo.title ?: ""
        holder.description.text = factInfo.description ?: ""
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * View holder for Adapter class
     */
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)
    }
}