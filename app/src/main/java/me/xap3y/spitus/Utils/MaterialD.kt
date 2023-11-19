package me.xap3y.spitus.Utils

import android.content.Context
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import me.xap3y.spitus.R


@Suppress("DEPRECATION")
class MaterialD {
    companion object {
        fun snackBar(context: Context?, text: String, view: View) {
            if (context != null) {
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(context!!.resources.getColor(R.color.snackBarTint))
                    .setActionTextColor(context!!.getColor(R.color.text_color))
                    .setAction("Action", null).show()
            } else {
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        }
        fun dialog(context: Context?, title: String, message: String, neutralB: Boolean? = true, negativeB: Boolean? = true, positiveB: Boolean? = true, cancelAble: Boolean? = true) {
            if (context == null) return
            var builder = MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)

            if (neutralB != null && neutralB) {
                builder
                    .setNeutralButton(context.resources.getString(R.string.cancelButton)) { dialog, which ->
                        // Respond to neutral button press
                    }
            }

            if(cancelAble != null) {
                builder.setCancelable(cancelAble)
            }

            if (negativeB != null && negativeB) {
                builder
                    .setNegativeButton(context.resources.getString(R.string.declineButton)) { dialog, which ->
                        // Respond to negative button press
                    }
            }
            if (positiveB != null && positiveB) {
                builder
                    .setPositiveButton(context.resources.getString(R.string.acceptButton)) { dialog, which ->
                        // Respond to positive button press
                    }
            }
            builder.show()
        }
    }

}