package com.flaviotps.provedor.extensions

import android.view.View

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.show(){
    this.visibility = View.VISIBLE
}