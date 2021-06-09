package com.bangkit.electrateam.qualityumapp.ui.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var token: String? = null
}