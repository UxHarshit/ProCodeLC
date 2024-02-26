package com.uxharshit.procodelc.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompanyInfoModelItem(
    @SerializedName("Acceptance")
    val Acceptance: String,
    @SerializedName("Difficulty")
    val Difficulty: String,
    @SerializedName("Frequency")
    val Frequency: String,
    @SerializedName("ID")
    val ID: String,
    @SerializedName("Leetcode Question Link")
    val Leetcode_Question_Link: String,
    @SerializedName("Title")
    val Title: String
)