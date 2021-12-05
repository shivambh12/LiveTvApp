package com.example.livetv.adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.livetv.Details
import com.example.livetv.R
import com.example.livetv.models.channel

class channeladapter(private  val items:ArrayList<channel>,private  val type:String):RecyclerView.Adapter<NewsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view:View
        if(type=="slider") {
            view = LayoutInflater.from(parent.context).inflate(R.layout.big_slider_view, parent, false)
        }
        else
            view = LayoutInflater.from(parent.context).inflate(R.layout.new_cat_view, parent, false)
        val viewholder=NewsViewHolder(view)
        return viewholder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val curentirem=items[position]
        holder.titleview.text=curentirem.name
        Glide.with(holder.itemView.context).load(curentirem.thumbnail).into(holder.imageview)
        holder.itemView.setOnClickListener {
            val intent=Intent(it.context,Details::class.java)
            intent.putExtra("channel",items[position])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleview: TextView =itemView.findViewById(R.id.channelName)
    val imageview: ImageView =itemView.findViewById(R.id.channelThumbnail)
}
