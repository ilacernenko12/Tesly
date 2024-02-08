package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("Cur_Abbreviation")
    val abbreviation: String,
    @SerializedName("Cur_ID")
    val id: Int,
    @SerializedName("Cur_Name")
    val name: String,
    @SerializedName("Cur_OfficialRate")
    val officialRate: Double,
    @SerializedName("Cur_Scale")
    val scale: Int,
    @SerializedName("Date")
    val date: String
)