package com.paxcreation.weatherapp.presentation.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import com.paxcreation.weatherapp.R


fun Activity.createLoadingDialog(): Dialog {
    val dialog = Dialog(this, R.style.ProgressDialog)
    dialog.let {
        it.setContentView(R.layout.dialog_progress)
        it.findViewById<ProgressBar>(R.id.progressBar2)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}

fun Context.showDialog(
    title: String? = null,
    message: String,
    positiveText: String? = null,
    onClickPositive: (() -> Unit)? = null,
    negativeText: String? = null,
    onClickNegative: (() -> Unit)? = null,
    neutralText: String? = null,
    onClickNeutral: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this).apply {
        if (!title.isNullOrEmpty()) {
            setTitle(title)
        }
        setMessage(message)
        if (positiveText != null) {
            setPositiveButton(positiveText) { _, _ -> onClickPositive?.invoke() }
        }
        if (negativeText != null) {
            setNegativeButton(negativeText) { _, _ -> onClickNegative?.invoke() }
        }
        if (neutralText != null) {
            setNeutralButton(neutralText) { _, _ -> onClickNeutral?.invoke() }
        }
    }
    builder.show()
}


fun Context.showToast(message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showToast(messageIds: Int?) {
    if (messageIds != null) {
        Toast.makeText(this, getString(messageIds), Toast.LENGTH_SHORT).show()
    }
}