package utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun isConnected(ctx: Context): Boolean {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected

        return isConnected
    }

}