package com.nyt.nytnews.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nyt_stories")
data class Result(
        @PrimaryKey
        @SerializedName("url")
        var url: String = "",
        @SerializedName("abstract")
        var abstractText: String = "",
        @SerializedName("created_date")
        var createdDate: String = "",
        @SerializedName("multimedia")
        var multimedia: List<Multimedia> = emptyList(),
        @SerializedName("published_date")
        var publishedDate: String = "",
        @SerializedName("section")
        var section: String = "",
        @SerializedName("short_url")
        var shortUrl: String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("updated_date")
        var updatedDate: String = "",
        @SerializedName("uri")
        var uri: String = "",
        @SerializedName("bookmarked")
        var bookmarked: Boolean = false,
        @SerializedName("displayDate")
        var displayDate : String =""
)

//        @SerializedName("kicker")
//        var kicker: String = "",
//        @SerializedName("material_type_facet")
//        var materialTypeFacet: String = "",
//        @SerializedName("subsection")
//        var subsection: String = "",
//        @SerializedName("byline")
//        var byline: String = "",
//@SerializedName("item_type")
//var itemType: String = "",