package com.worldonetop.growstudent.view.dialog

import android.app.Dialog
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.view.bottom.TabOneAdapter

class RoomDialog(val dialog: Dialog){
    fun setRv(adapter:TabOneAdapter, cancelListener:(()->Unit)?):Dialog{
        dialog.findViewById<RecyclerView>(R.id.dialogRoomRv).adapter = adapter
        cancelListener?.let { listener ->
            dialog.findViewById<Button>(R.id.dialogRoomClose).setOnClickListener{
                dialog.dismiss()
                listener()
            }

        }
        return dialog
    }
}
