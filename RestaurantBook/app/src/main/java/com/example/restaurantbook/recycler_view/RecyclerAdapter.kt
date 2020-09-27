package com.example.restaurantbook.recycler_view

import android.system.Os.bind
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantbook.R
import kotlinx.android.synthetic.main.restaurant_list.view.*

class RecyclerAdapter(private val items : ArrayList<restaurantList>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked $item.title", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list, parent, false)
            return RecyclerAdapter.ViewHolder(inflatedView)
        }


    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        private var view : View = v
        fun bind(listener: View.OnClickListener, item : restaurantList){
            view.img_restaurant_list.setImageDrawable(item.image)
            view.title_restaurant_list.text = item.title
            view.setOnClickListener(listener)
        }
    }
}