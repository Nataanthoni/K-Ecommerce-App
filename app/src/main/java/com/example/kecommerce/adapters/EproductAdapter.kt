package com.example.kecommerce.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kecommerce.R
import com.example.kecommerce.models.Eproducts

class EproductAdapter(val Eproducts: ArrayList<Eproducts>): RecyclerView.Adapter<EproductAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EproductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent,  false)

        context = v.context

        val viewHolder = ViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: EproductAdapter.ViewHolder, position: Int) {
        val eProducts = Eproducts[position]

        Glide.with(context!!).load(eProducts.image).into(holder.imageProduct)

    }


    override fun getItemCount(): Int {
        return Eproducts.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
          val imageProduct = itemView.findViewById<ImageView>(R.id.iv_product_image)
    }

}
