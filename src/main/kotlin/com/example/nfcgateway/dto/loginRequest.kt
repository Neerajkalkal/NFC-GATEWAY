package com.example.nfcgateway.dto

data class loginRequest(val email: String, val password: String)
data class changePasswordRequest(val oldPassword: String, val newPassword: String)
//data class NfcRequest(val phoneNfcId : String)