package com.app.easemypost.data.api

import com.app.easemypost.common.ApiConstant
import com.app.easemypost.domain.model.requests.AdminSignUpReq
import com.app.easemypost.domain.model.response.AdminSignUpRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST(ApiConstant.ADMIN_SIGNUP)
    suspend fun adminSignUp(
        @Body adminSignUpReq:AdminSignUpReq
    ):Response<AdminSignUpRes>


}