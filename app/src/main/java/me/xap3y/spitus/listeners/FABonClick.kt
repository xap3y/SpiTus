package me.xap3y.spitus.listeners

import android.R
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import me.xap3y.spitus.ui.developer.DeveloperFragment


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