package com.ahe.scdesign

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_icon_view.setOnClickListener {
            //startActivity(Intent(this,IconViewActivity::class.java))
            startActivity(Intent(this,BottomSheetActivity::class.java))
        }
    }
}
