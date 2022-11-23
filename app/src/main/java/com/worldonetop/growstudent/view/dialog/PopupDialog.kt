package com.worldonetop.growstudent.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.view.bottom.TabOneAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class PopupDialog(val dialog:Dialog){
    var itemCount:Int = 0
    fun setGadgetLayout(item: Item, listener:(Item) ->Unit):Dialog{
        dialog.findViewById<ImageView>(R.id.dialogPopupImage).setImageDrawable(item.img)
        dialog.findViewById<TextView>(R.id.dialogPopupTitle).text = item.name
        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).text = if(item.apply)
            "해제"
        else
            "사용"

        var content = "${item.content}\n\n"
        content += if(item.id < 100){
            "속도 증가"
        }else if(item.id < 200){
            "휴식 효율 증가"
        }else if(item.id < 300){
            "학업 효율 증가"
        }else if(item.id < 400){
            itemCount = item.num
            "남은 수량 : $itemCount"
        }else{
            ""
        }
        dialog.findViewById<TextView>(R.id.dialogPopupContent).text = content

        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).setOnClickListener{
            if(item.id >=300){
                itemCount -= 1
                content = "${item.content}\n\n" + "남은 수량 : $itemCount"
                dialog.findViewById<TextView>(R.id.dialogPopupContent).text = content
            }
            listener(item)
        }
        dialog.findViewById<TextView>(R.id.dialogPopupTitle).visibility = View.VISIBLE
        dialog.findViewById<Button>(R.id.dialogPopupBtnRight).visibility = View.GONE
        dialog.findViewById<ImageView>(R.id.dialogPopupClose).visibility = View.VISIBLE

        return dialog
    }
    fun setByGadgetLayout(item: Item, listener:(Item) ->Unit):Dialog{
        setGadgetLayout(item,listener)
        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).text = "구매"
        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).setOnClickListener{
            var content = "${item.content}\n\n"

            if(item.id >=300){
                itemCount += 1
                content = "${item.content}\n\n" + "남은 수량 : $itemCount"
                dialog.findViewById<TextView>(R.id.dialogPopupContent).text = content
            }
            listener(item)
        }
        return dialog
    }
    fun setInfoLayout(img:Drawable?, conent:String, leftStr:String, rightStr:String, leftListener:(() ->Unit)?,rightListener:(() ->Unit)?):Dialog{
        dialog.findViewById<ImageView>(R.id.dialogPopupImage).setImageDrawable(img)
        dialog.findViewById<TextView>(R.id.dialogPopupContent).text = conent
        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).text = leftStr
        dialog.findViewById<Button>(R.id.dialogPopupBtnRight).text = rightStr


        dialog.findViewById<Button>(R.id.dialogPopupBtnLeft).setOnClickListener{leftListener?.let { it() }}
        dialog.findViewById<Button>(R.id.dialogPopupBtnRight).setOnClickListener{rightListener?.let { it() }}
        dialog.findViewById<TextView>(R.id.dialogPopupTitle).visibility = View.GONE
        dialog.findViewById<Button>(R.id.dialogPopupBtnRight).visibility = View.VISIBLE
        dialog.findViewById<ImageView>(R.id.dialogPopupClose).visibility = View.GONE
        return dialog
    }

    
}
