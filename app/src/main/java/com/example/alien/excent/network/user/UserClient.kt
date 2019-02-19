package com.example.alien.excent.network.user

import com.example.alien.excent.data.NetworkResult
import com.example.alien.excent.network.NetworkApi
import com.example.alien.excent.network.ResponseConversions
import io.reactivex.Single
import javax.inject.Inject

class UserClient @Inject
internal constructor(
    private val api: NetworkApi,
    private val mapper: UserClientMapper
) {

    fun changePassword(oldPassword: String, newPassword: String): Single<NetworkResult> {
        return api.changePassword(mapper.toChangePasswordRequest(oldPassword, newPassword))
            .toSingleDefault(NetworkResult.SUCCESS)
            .onErrorReturn{ ResponseConversions().toNetworkResult(it) }
    }
}