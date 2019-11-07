package com.example.kecommerce.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kecommerce.R

class DialogItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_item)

        val ss:String = intent.getStringExtra("PRD_NAME")

        Log.d("TAG", "PRD NAME"+ss)
    }
}
