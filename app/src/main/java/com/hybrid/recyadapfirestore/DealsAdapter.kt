package com.hybrid.recyadapfirestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.dealsday.view.*

class DealsAdapter(
    mContext: Context,
    commentsClassadapter: List<DealModel>

) :
    RecyclerView.Adapter<DealsAdapter.MyViewHolder?>() {
    private val commentsClassadapter: List<DealModel>
    var mContext: Context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var name: TextView
        lateinit var price: TextView
        lateinit var image: ImageView

        init {

            name = view.drugname
            price = view.dprice
            image = view.drugimage
        }


    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.dealsday, parent, false)
        return MyViewHolder(itemView)
    }



    //constructor
    init {
        this.commentsClassadapter = commentsClassadapter
        this.mContext = mContext
    }

    override fun getItemCount(): Int {
      return commentsClassadapter.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val deal: DealModel = commentsClassadapter[position]

        holder.name.setText(deal.getName())
        holder.price.setText(deal.getPrice())
        holder.image.setImageResource(R.drawable.signpa)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(holder.itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(deal.image)
            .into(holder.itemView.drugimage)

        holder.itemView.setOnClickListener {

            Toast.makeText(mContext,"You clicked $position",Toast.LENGTH_SHORT).show()
        }

    }


}
