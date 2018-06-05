package io.freshworks.eao.data.repo.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkStateRepo(
        private val context: Context
) : NetworkStateDataSource {

    override fun connectedToInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        cm?.let {
            val netInfo = it.activeNetworkInfo
            netInfo?.let { return netInfo.isConnected }
        }
        return false
    }

}