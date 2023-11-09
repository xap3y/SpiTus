package me.xap3y.spitus.listeners

import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.util.Log

class FABonClick(private val fab: FloatingActionButton) : View.OnClickListener {
    override fun onClick(view: View?) {
        if (view != null) {
            Snackbar.make(view, "FAB Clicked", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
            Log.d("FABonClick", "Snackbar showed")

        }
        else{
            Log.d("FABonClick", "ERROR! `view` is null! (FABonClick:10,15)")
        }
    }
}