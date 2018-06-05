package io.freshworks.eao.data.repo.network

interface NetworkStateDataSource {
    fun connectedToInternet() : Boolean
}