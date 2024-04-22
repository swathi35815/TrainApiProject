package com.example.trainproject.trainSearch

import com.google.gson.annotations.SerializedName

data class TrainData(
    @SerializedName("train_num")
    val trainNum: Int,
    val name: String,
    @SerializedName("train_from")
    val trainFrom: String,
    @SerializedName("train_to")
    val trainTo: String,
    val data: TrainDataDetails
)

data class TrainDataDetails(
    val days: Map<String, Int>
)