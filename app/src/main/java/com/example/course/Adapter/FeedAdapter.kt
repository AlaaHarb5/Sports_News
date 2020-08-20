package com.example.course.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import  android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.course.Interface.ItemClickListner
import com.example.course.Model.RSSObject
import com.example.course.R
import kotlinx.android.synthetic.main.row.view.*
import java.util.zip.Inflater

@Suppress("DEPRECATION")
 class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener {
     var txtTiltle: TextView
     var Pubdate: TextView
     var txtcontent: TextView
     var itemclicklistner: ItemClickListner? = null

     init {
         txtTiltle = itemView.findViewById(R.id.txttitle_id) as TextView
         Pubdate = itemView.findViewById(R.id.date_id) as TextView
         txtcontent = itemView.findViewById(R.id.content_id) as TextView
         itemView.setOnClickListener(this)
         itemView.setOnLongClickListener(this)

     }

     fun setItemClicklistner(itemClickListner: ItemClickListner) {
         this.itemclicklistner = itemclicklistner
     }

     override fun onClick(v: View?) {
         this.itemclicklistner!!.onClick(v, adapterPosition, false)
     }


     override fun onLongClick(v: View?): Boolean {
         this.itemclicklistner!!.onClick(v, adapterPosition, false)
         return true
     }
 }
class FeedAdapter (private val rssObject: RSSObject, private val mcontext:Context):RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater: LayoutInflater
    init {
        inflater= LayoutInflater.from(mcontext)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView=inflater.inflate(R.layout.row,parent,false)
        return FeedViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTiltle.text=rssObject.items[position].title
        holder.Pubdate.text=rssObject.items[position].pubDate
        holder.txtcontent.text=rssObject.items[position].content
        holder.setItemClicklistner(ItemClickListner{view, position, islongCliclk ->
            if(!islongCliclk)
            {
                val browserIntent=Intent(Intent.ACTION_VIEW,Uri.parse(rssObject.items[position].link))
                mcontext.startActivity(browserIntent)
            }
        })
    }

}