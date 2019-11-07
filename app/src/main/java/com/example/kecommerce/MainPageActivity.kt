package com.example.kecommerce

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kecommerce.activities.ProfileActivity
import com.example.kecommerce.adapters.ProductAdapter
import com.example.kecommerce.models.Product
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.app_bar_main_page.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kecommerce.fragments.CartFragment
import com.example.kecommerce.fragments.ProductFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainPageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var fbAuth = FirebaseAuth.getInstance()

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainPageActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        var dialog: Dialog? = null

    }

    lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        showDialog()

        setSupportActionBar(toolbar)

        addFragment(supportFragmentManager, ProductFragment.newInstance(), R.id.frame_content)

        fab.setOnClickListener { view ->
//            dialog!!.show()
            firebaseRef = FirebaseDatabase.getInstance().reference
            storeProductsToDB(firebaseRef)

        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)



    }

    private fun storeProductsToDB (firebaseData: DatabaseReference) {

        val products : List<Product> = mutableListOf(
            Product("Expensive Jacket","KES 100","https://static.independent.co.uk/s3fs-public/thumbnails/image/2019/04/04/10/whistles-leather-jacket.png"),
            Product("Cheap Jacket","KES 1","https://www.pngarts.com/files/3/Men-Jacket-PNG-Picture.png"),
            Product("Leather Jacket", "KES 1200", "https://5.imimg.com/data5/MI/SA/MY-8637222/mens-black-leather-jacket-500x500.png"),
            Product("Metal Jacket","KES 450","https://www.pngarts.com/files/3/Black-Biker-Leather-Jacket-PNG-Image-Background.png")
        )

        products.forEach {
            val key: String = firebaseData.child("products").push().key!!
            it.uuid = key
            firebaseData.child("products").child(key).setValue(it)
        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.nav_home -> {
                Toast.makeText(this, "Home Clicked", Toast.LENGTH_LONG).show()
                addFragment(supportFragmentManager, ProductFragment.newInstance(),R.id.frame_content)
            }
            R.id.nav_cart -> {
                Toast.makeText(this, "Cart Clicked", Toast.LENGTH_LONG).show()
                addFragment(supportFragmentManager, CartFragment.newInstance(),R.id.frame_content)
            }
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_LONG).show()

                val intentProfile = Intent(this, ProfileActivity::class.java)
                startActivity(intentProfile)
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout Clicked", Toast.LENGTH_LONG).show()

                FirebaseAuth.getInstance().signOut()

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun addFragment(manager: FragmentManager,  fragment: Fragment, frameID: Int) {
        var transaction = manager.beginTransaction()
        transaction.replace(frameID, fragment)
        transaction.commit()
    }

    inner class RecyclerViewItemDecorator(private val spaceInPixels: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = spaceInPixels
            outRect.right = spaceInPixels
            outRect.bottom = spaceInPixels

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = spaceInPixels
            } else {
                outRect.top = 0
            }
        }
    }

    private fun showDialog() {
        dialog = Dialog(this)
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

        textView.text = "joy"

        yesBtn.setOnClickListener {
//            val intent = Intent(this, JoyActivity::class.java)
//            startActivity(intent)

            dialog!!.dismiss()
        }
//        noBtn.setOnClickListener { dialog .dismiss() }
//        dialog .show()

    }

//    class GridItemDecoration(gridSpacingPx: Int, gridSize: Int) : RecyclerView.ItemDecoration() {
//        private var mSizeGridSpacingPx: Int = gridSpacingPx
//        private var mGridSize: Int = gridSize
//
//        private var mNeedLeftSpacing = false
//
//        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//            val frameWidth = ((parent.width - mSizeGridSpacingPx.toFloat() * (mGridSize - 1)) / mGridSize).toInt()
//            val padding = parent.width / mGridSize - frameWidth
//            val itemPosition = (view.getLayoutParams() as RecyclerView.LayoutParams).viewAdapterPosition
//            if (itemPosition < mGridSize) {
//                outRect.top = 0
//            } else {
//                outRect.top = mSizeGridSpacingPx
//            }
//            if (itemPosition % mGridSize == 0) {
//                outRect.left = 0
//                outRect.right = padding
//                mNeedLeftSpacing = true
//            } else if ((itemPosition + 1) % mGridSize == 0) {
//                mNeedLeftSpacing = false
//                outRect.right = 0
//                outRect.left = padding
//            } else if (mNeedLeftSpacing) {
//                mNeedLeftSpacing = false
//                outRect.left = mSizeGridSpacingPx - padding
//                if ((itemPosition + 2) % mGridSize == 0) {
//                    outRect.right = mSizeGridSpacingPx - padding
//                } else {
//                    outRect.right = mSizeGridSpacingPx / 2
//                }
//            } else if ((itemPosition + 2) % mGridSize == 0) {
//                mNeedLeftSpacing = false
//                outRect.left = mSizeGridSpacingPx / 2
//                outRect.right = mSizeGridSpacingPx - padding
//            } else {
//                mNeedLeftSpacing = false
//                outRect.left = mSizeGridSpacingPx / 2
//                outRect.right = mSizeGridSpacingPx / 2
//            }
//            outRect.bottom = 0
//        }
//    }
}
