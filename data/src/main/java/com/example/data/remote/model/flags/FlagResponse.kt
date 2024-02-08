package com.example.data.remote.model.flags

import com.google.gson.annotations.SerializedName

data class FlagResponse(
    @SerializedName("flags")
    val flag: Flags
)

data class Flags(
    val png: String,
    val svg: String,
    val alt: String
)
