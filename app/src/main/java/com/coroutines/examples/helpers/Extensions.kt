package com.coroutines.examples.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun AppCompatActivity.showSnackbar(snackbarText: String, timeLength: Int = 2000) {
    Snackbar.make(findViewById<View>(android.R.id.content), snackbarText, timeLength).show()
}

fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = 2000) {
    (activity as AppCompatActivity).showSnackbar(snackbarText, timeLength)
}


fun Context.openActivity(className: Class<*>, bundle: Bundle? = null, closeAll: Boolean = false) {

    val intent = Intent(this, className)
    bundle?.let {
        intent.putExtras(it)
    }

    if (closeAll) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    startActivity(intent)

}

fun Context.openActivityForResult(
    className: Class<*>,
    bundle: Bundle? = null,
    requestCode: Int = 0
) {

    val intent = Intent(this, className)
    bundle?.let {
        intent.putExtras(it)
    }


    try {
        (this as AppCompatActivity).startActivityForResult(intent, requestCode)
    } catch (e: ClassCastException) {

    }

}

fun Fragment.openActivityForResult(
    className: Class<*>,
    bundle: Bundle? = null,
    requestCode: Int = 0
) {

    val intent = Intent(activity, className)
    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, requestCode)

}

fun ViewGroup.inflate(id: Int): View {
    return LayoutInflater.from(context).inflate(id, this, false)
}

fun Date.getCurrentDateForBackEnd(format: String = "yyyy-MM-dd hh:mm:ss"): String? {
    return DateFormat.format(format, time).toString()
}


fun Context.convertArrayResourceToList(resID: Int): List<String> {
    return resources.getStringArray(resID).toList()

}

fun AppCompatEditText.isValidEmail(): Boolean {
    val email = text.toString()
    return if (email.isEmpty()) true
    else android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun AppCompatEditText.isValidMobile(): Boolean {
    val mobile = text.toString()
    return mobile.length == 11
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

@SuppressLint("RestrictedApi")
fun AppCompatActivity.validateActionBar(title: String = "") {
    supportActionBar?.apply {
        setTitle(title)
        setDefaultDisplayHomeAsUpEnabled(true)
        setDisplayHomeAsUpEnabled(true)
    }
}





fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun Context.getColorRes(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

fun Context.dp(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
















