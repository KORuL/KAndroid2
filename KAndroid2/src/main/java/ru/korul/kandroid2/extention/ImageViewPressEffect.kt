package ru.korul.kandroid2.extention

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat


fun ImageView.imgPress() {
    setOnTouchListener(imgPressTouch())
}

fun imgPressTouch(): View.OnTouchListener {
    return imgPress(0x77eeddff) //DEFAULT color
}

private fun imgPress(color: Int): View.OnTouchListener {
    return object : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val view: ImageView = v as ImageView
                    view.drawable.colorFilter =
                        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
                    view.invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    v.performClick()
                    run {
                        val view: ImageView = v as ImageView

                        //Clear the overlay
                        view.drawable.clearColorFilter()
                        view.invalidate()
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    val view: ImageView = v as ImageView
                    view.drawable.clearColorFilter()
                    view.invalidate()
                }
            }
            return true
        }
    }
}
