package com.paxcreation.weatherapp.presentation.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.paxcreation.weatherapp.R

class ToolbarCustomView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {

    private val viewGroup: View =
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true)
    private val title = viewGroup.findViewById(R.id.text_title_toolbar) as TextView
    private val backgroundColor =
        viewGroup.findViewById(R.id.toolbar) as ConstraintLayout
    private val iconLeft = viewGroup.findViewById(R.id.button_back) as ImageView
    private val iconRight = viewGroup.findViewById(R.id.button_menu) as ImageView

    init {
        val typedArray =
            context?.obtainStyledAttributes(attributeSet, R.styleable.ToolbarCustomView, 0, 0)
        try {
            val mTitle = typedArray?.getString(R.styleable.ToolbarCustomView_toolbar_title)
            val mBackground =
                typedArray?.getColor(R.styleable.ToolbarCustomView_toolbar_background_color, 0)
            val mIconLeft =
                typedArray?.getDrawable(R.styleable.ToolbarCustomView_toolbar_left_image)
            val mIconRight =
                typedArray?.getDrawable(R.styleable.ToolbarCustomView_toolbar_right_image)
            val mShowIconLeft =
                typedArray?.getBoolean(R.styleable.ToolbarCustomView_toolbar_show_left, false)
            val mShowIconRight =
                typedArray?.getBoolean(R.styleable.ToolbarCustomView_toolbar_show_right, false)

            title.text = mTitle
            if (mBackground != null) {
                backgroundColor.setBackgroundColor(mBackground)
            }

            if (mIconLeft != null) {
                iconLeft.setImageDrawable(mIconLeft)
            }

            if (mIconRight != null) {
                iconRight.setImageDrawable(mIconRight)
            }

            if (mShowIconLeft == true) {
                iconLeft.visibility = View.VISIBLE
            } else {
                iconLeft.visibility = View.INVISIBLE
            }

            if (mShowIconRight == true) {
                iconRight.visibility = View.VISIBLE
            } else {
                iconRight.visibility = View.INVISIBLE
            }

        } finally {
            typedArray?.recycle()
        }
    }

    fun setOnClickRight(onClickListener: OnClickListener) {
        iconRight?.setOnClickListener(onClickListener)
    }

    fun setOnClickLeft(onClickListener: OnClickListener) {
        iconLeft?.setOnClickListener(onClickListener)
    }

}