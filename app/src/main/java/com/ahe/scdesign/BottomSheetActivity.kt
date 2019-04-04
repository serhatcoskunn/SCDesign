package com.ahe.scdesign

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat.setLayerType
import android.view.View
import android.view.View.LAYER_TYPE_SOFTWARE
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class BottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        var paint=Paint()
        paint.color=Color.BLUE
        //buttonShadow.setLayerType(View.LAYER_TYPE_SOFTWARE, paint)
        buttonShadow.setShadowLayer(30f, 0f, 0f, Color.RED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            buttonShadow.setLayerType(LAYER_TYPE_SOFTWARE, paint);
        }
    }
}
