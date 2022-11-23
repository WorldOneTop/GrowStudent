package com.worldonetop.growstudent.di

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.ImageView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.util.MySize
import com.worldonetop.growstudent.view.dialog.LoadingDialog
import com.worldonetop.growstudent.view.dialog.PopupDialog
import com.worldonetop.growstudent.view.dialog.RoomDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class DialogModule {

    @ActivityScoped
    @Provides
    fun provideLoadingDialog(@ActivityContext context: Context): LoadingDialog {
        return LoadingDialog(
            Dialog(context).apply {
                setContentView(R.layout.dialog_loading)
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게
                (findViewById<ImageView>(R.id.loadingDialogAni).background as AnimationDrawable).start()
                setCanceledOnTouchOutside(false)
                setCancelable(false)
            }
        )
    }
    @ActivityScoped
    @Provides
    fun providePopupDialog(@ActivityContext context: Context): PopupDialog {
        return PopupDialog(
            Dialog(context).apply {
                setContentView(R.layout.dialog_popup)
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게
                window!!.setLayout((MySize.width - 80*MySize.density).toInt(), (MySize.height - 300*MySize.density).toInt() )
                setCanceledOnTouchOutside(false)
                findViewById<ImageView>(R.id.dialogPopupClose).setOnClickListener{this.dismiss()}
            }
        )
    }
    @ActivityScoped
    @Provides
    fun provideRoomDialog(@ActivityContext context: Context): RoomDialog {
        return RoomDialog(
            Dialog(context).apply {
                setContentView(R.layout.dialog_room)
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게
                window!!.setLayout((MySize.width - 80*MySize.density).toInt(), (MySize.height - 300*MySize.density).toInt() )
                setCanceledOnTouchOutside(false)
                findViewById<Button>(R.id.dialogRoomClose).setOnClickListener{this.dismiss()}
            }
        )
    }

}