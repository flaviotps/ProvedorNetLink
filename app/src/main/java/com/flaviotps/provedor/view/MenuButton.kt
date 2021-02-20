package com.flaviotps.provedor.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.flaviotps.provedor.R

class MenuButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val label: TextView
    private val icon: ImageView
    var view: View = LayoutInflater.from(context).inflate(R.layout.menu_button, this, true)

    init {
        label = view.findViewById(R.id.label)
        icon = view.findViewById(R.id.icon)
        isClickable = true
        isFocusable = true
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.MenuButton, 0, 0).let {
            icon.apply {
                setImageDrawable(it.getDrawable(R.styleable.MenuButton_icon))
                scaleX = it.getFloat(R.styleable.MenuButton_iconScale,1.0f)
                scaleY = it.getFloat(R.styleable.MenuButton_iconScale, 1.0f)
            }

            label.text = it.getText(R.styleable.MenuButton_android_text)
            label.text = it.getText(R.styleable.MenuButton_android_text)
        }
    }

}