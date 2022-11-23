package com.worldonetop.growstudent.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.worldonetop.growstudent.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class LoadingDialog(val dialog:Dialog){
//    val dialog: Dialog
//    init {
//        dialog = Dialog(context).apply {
//            setContentView(R.layout.dialog_loading)
//            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게
//            (findViewById<ImageView>(R.id.loadingDialogAni).background as AnimationDrawable).start()
//            setCanceledOnTouchOutside(false)
//            setCancelable(false)
//        }
//    }
//    fun show() {
//        dialog.show()
//    }
//
//    fun dismiss() {
//        dialog.dismiss()
//    }
}