package com.nyt.nytnews.utility

import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Fade
import androidx.transition.Slide

object Utility {
    private fun Fragment.applyTransaction(): Fragment {
        val startAnimation = Slide(Gravity.END)
        startAnimation.duration = 400L

        val endAnimation = Fade()
        endAnimation.duration = 200L

        this.enterTransition = startAnimation
        this.exitTransition = endAnimation

        return this
    }

    fun navigateFragment(fragManager: FragmentManager, containerID: Int, fragment: Fragment, tag: String, addToBackStack: Boolean = true) {

        val transaction = fragManager.beginTransaction().replace(containerID, fragment.applyTransaction())

        if(addToBackStack)
            transaction.addToBackStack(tag)

        transaction.commit()
    }
}