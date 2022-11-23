package com.worldonetop.growstudent.di

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import com.worldonetop.growstudent.util.MySize
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MySizeModule {

    @Singleton
    @Provides
    fun provideMapAPI(@ApplicationContext context: Context): MySize {
        val density = context.resources.displayMetrics.density
        MySize.width = getScreenWidth(context)
        MySize.height = getScreenHeight(context)
        MySize.density = density
        MySize.appBarHeight = 50*density
        MySize.mapWith = 1606*density * 2
        MySize.mapHeight = 1133*density * 2
        MySize.userWith = 73*density
        MySize.userHeight = 103*density
        MySize.statusBarHeight = getStatusBarHeight(context)
        MySize.mapViewHeight = MySize.height - MySize.statusBarHeight*1f
        return MySize
    }
    private fun getStatusBarHeight(context: Context): Int {
        val screenSizeType = context.resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK
        var statusbar = 0
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusbar = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return statusbar
    }
    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }
    private fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }
}