package com.hariharan.aboutcanada.data.model

import com.google.gson.annotations.SerializedName

/**
 * Model class for JSON response
 */
data class Facts(
    @SerializedName("title") var title: String? = "",
    @SerializedName("rows") var facts: ArrayList<ItemInfo> = arrayListOf()
)
