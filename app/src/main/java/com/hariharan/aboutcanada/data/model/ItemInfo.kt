package com.hariharan.aboutcanada.data.model

import com.google.gson.annotations.SerializedName

/**
 * data class for rows in JSON response
 */
data class ItemInfo(
    @SerializedName("title") var title: String? = "",
    @SerializedName("description") var description: String? = "",
    @SerializedName("imageHref") var imageUrl: String? = ""
)
