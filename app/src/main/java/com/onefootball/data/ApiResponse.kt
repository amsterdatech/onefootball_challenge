package com.onefootball.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(@SerializedName("news") val news: List<News>)