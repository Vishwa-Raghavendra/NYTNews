package com.nyt.nytnews.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

     fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}