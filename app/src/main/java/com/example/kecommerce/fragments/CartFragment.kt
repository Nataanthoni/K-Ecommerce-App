package com.example.kecommerce.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.kecommerce.R
import com.example.kecommerce.adapters.EproductAdapter
import com.example.kecommerce.models.Eproducts
import kotlinx.android.synthetic.main.fragment_cart.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CartFragment : Fragment() {

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    private var eproductAdapter: EproductAdapter? = null

    private val cart = ArrayList<Eproducts>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cart, container, false)


        addCartItems()

        eproductAdapter = EproductAdapter(cart)
        view.cartRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayout.VERTICAL, false)


        view.cartRecyclerView.adapter = eproductAdapter


        return view
    }

    private fun addCartItems(){
        cart.add(Eproducts("https://www.freepnglogos.com/uploads/google-logo-png-3.png"))
        cart.add(Eproducts("https://www.freepnglogos.com/uploads/google-logo-png-3.png"))
        cart.add(Eproducts("https://www.freepnglogos.com/uploads/google-logo-png-3.png"))
    }


}
