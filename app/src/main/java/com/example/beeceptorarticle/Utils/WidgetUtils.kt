package com.example.beeceptorarticle.Utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.beeceptorarticle.R
import kotlinx.android.synthetic.main.error_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick

object WidgetUtils {
    interface DialogButton {
        fun onClickButton1(dlg: Dialog)

    }

    fun showErrorDialog(
        context: Context?, title: String, textButton1: String,
        listener: DialogButton
    ) {

        val dialog = context?.let {
            Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.error_dialog)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                text_title?.text = title

                button_ok?.apply {
                    text = textButton1
                }.also {
                    it?.onClick { listener.onClickButton1(this@apply) }
                }

            }
        }
        dialog?.show()
    }

}