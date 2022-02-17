package com.a.hub.helper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.a.hub.R
import com.a.hub.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T : Activity> Activity.startClearActivity(activity: Class<*>?) {
    val intent = Intent(this, activity)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
    finish()
}

fun <T : Activity> Activity.startExternal(url: String) {
    if(url.isEmpty()) return
    val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(i)
}

fun BaseActivity.replaceFragment(container: Int, fragment: Fragment, tag: String){
    if(supportFragmentManager.findFragmentByTag(tag) == null) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout)
        transaction.replace(container, fragment, tag).commit()
    }
}

fun startDialogFragment (
    fragmentManager: FragmentManager,
    fragmentDialog: DialogFragment,
){
    val transaction : FragmentTransaction = fragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_down)
    fragmentDialog.show(transaction, fragmentDialog.javaClass.canonicalName)
}


inline fun Fragment.repeatWithViewLifecycle(
    minState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    if (minState == Lifecycle.State.INITIALIZED || minState == Lifecycle.State.DESTROYED) {
        throw IllegalArgumentException("minState must be between INITIALIZED and DESTROYED")
    }
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minState) {
            block()
        }
    }
}

fun View.toggle(): Boolean {
    this.visibility = if(visibility == View.VISIBLE) View.GONE else View.VISIBLE
    return visibility == View.VISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextView.setTextOrHide(txt: String?): Boolean {
    if(txt.isNullOrEmpty()){
        gone()
        return false
    }
    text = txt
    visible()
    return true
}

