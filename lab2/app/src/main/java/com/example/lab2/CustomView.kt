package com.example.lab2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.blue

class CustomView : View {
    var isSwitchOn: Boolean = false
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle:
    Int) : super(context, attrs, defStyle)
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()
        this.isSwitchOn = !isSwitchOn

        // Paint here.
        if(isSwitchOn) {
            paint.color = Color.BLUE
            paint.style = Paint.Style.FILL
            canvas.drawRect(100f, 100f, 300f, 300f, paint)
        }
        else {
            paint.color = Color.GRAY
            canvas.drawCircle(500f, 200f, 100f, paint)
        }

    }
}
