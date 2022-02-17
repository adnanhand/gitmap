package com.a.hub.ui.base

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.a.hub.R

abstract class BaseDrawerActivity: BaseActivity() {

    var drawerToggle: ActionBarDrawerToggle? = null
    var drawerLayout: DrawerLayout? = null

    protected var drawer: View? = null
    protected var content: View? = null

    protected var scale: Float = 0.2f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun initDrawer(d: DrawerLayout) {
        drawerLayout = d
        if (drawerLayout != null && toolbar != null){
            drawerLayout!!.drawerElevation = 0f
            drawerLayout!!.setScrimColor(Color.TRANSPARENT)
            drawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
            ) {
                override fun onDrawerClosed(view: View) {
                    super.onDrawerClosed(view) //setTitle(mTitle);
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                    onMenuClosed()
                }

                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView) //setTitle(mDrawerTitle);
                    drawerLayout!!.bringToFront()
                    drawerLayout!!.requestLayout()
                    invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
                    onMenuOpened()
                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    scaleContent(drawerView, slideOffset)
                    //translateContent(slideOffset)
                    //scaleContentHeight(slideOffset)

                }
            }

            drawerLayout?.addDrawerListener(drawerToggle!!)
            drawerToggle?.setDrawerIndicatorEnabled(true)
            drawerToggle?.syncState()

            drawerToggle?.setToolbarNavigationClickListener {}

        }
    }

    private fun scaleContent(drawerView: View, slideOffset: Float) {
        if(content != null) {
            // Scale the View based on current slide offset
            val diffScaledOffset = slideOffset * scale
            val offsetScale = 1 - diffScaledOffset
            content!!.scaleX = offsetScale
            content!!.scaleY = offsetScale
            // Translate the View, accounting for the scaled width
            val xOffset: Float = drawerView.getWidth() * slideOffset
            val xOffsetDiff = content!!.width * diffScaledOffset / 2
            val xTranslation = xOffset - xOffsetDiff
            content!!.translationX = xTranslation
        }
    }

    @SuppressWarnings("unused")
    private fun scaleContentHeight(drawerView: View, slideOffset: Float) {
        val params: DrawerLayout.LayoutParams = content?.layoutParams as DrawerLayout.LayoutParams
        val value: Int = (drawer?.getHeight()?.times(slideOffset)?.toInt()
            ?.times(0.3f))?.toInt() ?: 0
        params.height = drawer?.getHeight()?.minus(value)!!
        params.topMargin = (drawer?.getHeight()?.minus(params.height))?.div(2) ?: 0
        content?.layoutParams = params
    }
    @SuppressWarnings("unused")
    private fun translateContent(drawerView: View, slideOffset: Float) {
        content?.translationX = drawerView.width.times(slideOffset)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        if (drawerLayout != null) {
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout!!.closeDrawers()
                return
            } else if (drawerLayout!!.isDrawerOpen(GravityCompat.END)) {
                drawerLayout!!.closeDrawers()
                return
            }
        }
        super.onBackPressed()
    }

    protected open fun onMenuOpened(){}
    protected open fun onMenuClosed(){}
}