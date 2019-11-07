package com.example.kecommerce.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.kecommerce.R
import com.example.kecommerce.activities.DialogItemActivity
import com.example.kecommerce.models.Product

class ProductAdapter(val productList: List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var context: Context? = null

    companion object {

    }

    var dialog: Dialog?= null

    var prd_name: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        context = v.context
        val vH = ViewHolder(v)
        v.setOnClickListener {

            Log.d("TAG", "Product Clicked "+vH.txtName.text)
            var intent: Intent = Intent(context, DialogItemActivity::class.java)
            intent.putExtra("PRD_NAME", vH.txtName.text)
//            vH.textPrdName.text = vH.txtName.text
            prd_name = vH.txtName.text.toString()
            showDialog()
        }
        return vH

    }

    private fun showDialog() {
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.singleproduct)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        val txtJacket = dialog.findViewById(R.id.txtJacket) as ImageView
//        txtJacket.setOnClickListener { dialog .dismiss() }
//        dialog!!.show()
//        val body = dialog .findViewById(R.id.body) as TextView
//        body.text = title
        val yesBtn = dialog!!.findViewById<Button>(R.id.add_to_cart)
        var textView: TextView = dialog!!.findViewById<TextView>(R.id.textProductName)

        textView.text = prd_name

        yesBtn.setOnClickListener {
            //            val intent = Intent(this, JoyActivity::class.java)
//            startActivity(intent)

            dialog!!.dismiss()
        }
//        noBtn.setOnClickListener { dialog .dismiss() }
        dialog!! .show()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = productList[position].name
        holder.txtPrice.text = productList[position].price
        Glide.with(context!!).load(productList[position].image).into(holder.imageProduct)
    }



    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtPrice = itemView.findViewById<TextView>(R.id.txtPrice)
        var imageProduct = itemView.findViewById<ImageView>(R.id.txtJacket)
        var textPrdName = itemView.findViewById<TextView>(R.id.textProductName)
    }

}