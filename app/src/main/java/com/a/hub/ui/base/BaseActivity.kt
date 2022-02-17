package com.a.hub.ui.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.a.hub.R
import com.a.hub.app.ThemeHelper

abstract class BaseActivity: AppCompatActivity() {

    protected var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun initToolbar(t: Toolbar, title: String?) {
        toolbar = t
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            if (toolbar != null && title != null) {
                toolbar!!.setTitle(title)
            }
            title?.let { setTitle(it) }
        }
    }

    protected fun initToolbar(toolbar: Toolbar, title: String?, backButton: Boolean) {
        initToolbar(toolbar, title)
        initBackButton(backButton)
    }

    protected fun initBackButton(backButton: Boolean) {
        if (!backButton) return
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
        }
        if (toolbar != null) {
            toolbar!!.setNavigationOnClickListener { view: View? -> finish() }
        }
    }

    protected open fun initBackButton() {
        /*val back = findViewById<View>(R.id.back)
        back?.setOnClickListener { v: View? -> onBackPressed() }*/
    }

    protected fun checkTheme(themeSwitch: SwitchCompat?, view: View?, statusBarColor: Int) {
        if(view == null) return
        try {
            val darkMode: Boolean = ThemeHelper.isDarkMode(this)
            themeSwitch?.isChecked = darkMode
            setStatusBarColor(view, statusBarColor, !darkMode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun updateTheme(view: View?) {
        if(view == null) return
        try {
            val statusBarColor = ContextCompat.getColor(this, R.color.primaryVariant)
            if (statusBarColor != 0)
                setStatusBarColor(view, statusBarColor, !ThemeHelper.isDarkMode(this))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    protected fun fullTransparent() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    protected fun setStatusBarColor(view: View, color: Int, isLight: Boolean) {
        if (isLight) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
                /*window.insetsController?.setSystemBarsAppearance(
                    0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )*/
            }else{
                view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color //color = ContextCompat.getColor(this, R.color.black)
    }
}