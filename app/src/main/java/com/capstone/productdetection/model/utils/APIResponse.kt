package com.capstone.productdetection.model.utils

class APIResponse <T>(val status: StatusResponse, val body: T, val message: String?) {

    companion object {
        fun <T> success(body: T): APIResponse<T> =
            APIResponse(StatusResponse.SUCCESS, body, null)

        fun <T> empty(msg: String, body: T): APIResponse<T> =
            APIResponse(StatusResponse.EMPTY, body, msg)

        fun <T> error(msg: String, body: T): APIResponse<T> =
            APIResponse(StatusResponse.ERROR, body, msg)
    }
}