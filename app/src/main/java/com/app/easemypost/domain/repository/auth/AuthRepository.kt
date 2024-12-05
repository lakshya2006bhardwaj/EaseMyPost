package com.app.easemypost.domain.repository.auth

import com.app.easemypost.data.api.ApiHandler
import com.app.easemypost.data.api.ApiInterface
import com.app.easemypost.domain.model.requests.AdminLoginReq
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.requests.AdminVerifyOtpReq
import com.app.easemypost.domain.model.response.AdminLoginRes
import com.app.easemypost.domain.model.response.AdminSignUpRes
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiInterface:ApiInterface
) {
    suspend fun adminSignUp(adminSignupReq:AdminSignUpReq):ApiHandler<AdminSignUpRes> {
        return ApiHandler.handleApiCall{
            apiInterface.adminSignUp(adminSignUpReq = adminSignupReq)
        }
    }

    suspend fun adminLogin(adminLoginReq: AdminLoginReq):ApiHandler<AdminLoginRes> {
        return ApiHandler.handleApiCall{
            apiInterface.adminLogin(adminLoginReq = adminLoginReq)
        }
    }
    suspend fun adminVerifyOtp(adminVerifyOtpReq:AdminVerifyOtpReq):ApiHandler<AdminLoginRes>{
        return ApiHandler.handleApiCall {
            apiInterface.adminVerifyOtp(adminVerifyOtpReq)
        }
    }
}