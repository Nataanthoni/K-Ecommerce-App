package com.example.kecommerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.View
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedInActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoggedInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)


        btnSignOut.setOnClickListener { view ->
            showMessage(view, "Signing Out...")
            signOut()
        }
//        fbAuth.addAuthStateListener {
//            if (fbAuth.currentUser == null){
//                this.finish()
//            }
//        }
    }

    fun signOut(){
//        fbAuth.signOut()
        startActivity(MainActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
    }

    fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }

}
