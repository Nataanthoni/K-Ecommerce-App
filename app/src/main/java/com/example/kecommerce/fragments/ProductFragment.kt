package com.example.kecommerce.fragments


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.kecommerce.R
import com.example.kecommerce.activities.DialogItemActivity
import com.example.kecommerce.adapters.ProductAdapter
import com.example.kecommerce.models.Product
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_product.view.*

class ProductFragment : Fragment() {
    companion object {
        fun newInstance(): ProductFragment {
            return ProductFragment()
        }
    }

    private lateinit var products:List<Product>

    private lateinit var firebaseRef: DatabaseReference

    private lateinit var adapter: ProductAdapter

    private lateinit var recyclerViewProduct: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        firebaseRef = FirebaseDatabase.getInstance().reference.child("products")

        addProductItems()

        recyclerViewProduct = view.recyclerViewProducts

        recyclerViewProduct.layoutManager = GridLayoutManager(activity, 2)


//        val productsListener = object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val product = p0.getValue(Product::class.java)
//
//                products = listOf<Product>(
//
//                    Product(product!!.name, product.price, product.image, product.uuid)
//
//                )
//
//                Log.d("TAG", "PRODUCTS......$products")
//
////                adapter = ProductAdapter(products)
//            }
//
//
//
//        }

//        firebaseRef.addValueEventListener(productsListener)


//        recyclerViewProduct.adapter = adapter

        return view

    }


    private fun addProductItems() {

//        products = listOf<Product>(
//            Product("RainyJacket", "200", "https://www.freepnglogos.com/uploads/google-logo-png-3.png"),
//            Product("BlackLeather","300" ,"https://i.imgur.com/tGbaZCY.jpg"),
//            Product("CottonJacket","300","https://www.freepnglogos.com/uploads/google-logo-png-3.png"),
//            Product("BlackJacket","800","https://i.imgur.com/tGbaZCY.jpg")
//        )

        val option = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(firebaseRef, Product::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
                return ProductViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: ProductViewHolder, p1: Int, product: Product) {
                val productid = getRef(p1).key.toString()
                firebaseRef.child(productid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(context, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        holder.txtName.text = product.name
                        holder.txtPrice.text = product.price
                        Glide.with(context!!).load(product.image).into(holder.imageProduct)
                    }

                })

            }

        }

        recyclerViewProduct.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()


//        val productsListener = object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val product = p0.getValue(Product::class.java)
//
//                products = listOf<Product>(
//
//                    Product(product!!.name, product.price, product.image)
//
//                )
//            }
//
//        }
//
//        firebaseRef.addValueEventListener(productsListener)
    }

    class ProductViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val txtName = itemView!!.findViewById<TextView>(R.id.txtName)
        val txtPrice = itemView!!.findViewById<TextView>(R.id.txtPrice)
        var imageProduct = itemView!!.findViewById<ImageView>(R.id.txtJacket)
    }


}
