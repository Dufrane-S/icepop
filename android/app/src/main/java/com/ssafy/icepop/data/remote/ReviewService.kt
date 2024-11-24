package com.ssafy.icepop.data.remote

import com.ssafy.icepop.data.model.dto.IceCreamOrderReview
import com.ssafy.icepop.data.model.dto.request.ReviewListRequest
import com.ssafy.icepop.data.model.dto.request.ReviewPostRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewService {
    @POST("review/getReviews")
    suspend fun getReviewList(@Body reviewListRequest: ReviewListRequest) : List<IceCreamOrderReview>

    @POST("review/addReview")
    suspend fun postReview(@Body reviewPostRequest: ReviewPostRequest) : String
}