package com.nyt.nytnews.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nyt_stories")
data class Result(
        @SerializedName("abstract")
        var `abstract`: String = "",
        @SerializedName("byline")
        var byline: String = "",
        @SerializedName("created_date")
        var createdDate: String = "",
        @SerializedName("item_type")
        var itemType: String = "",
        @SerializedName("kicker")
        var kicker: String = "",
        @SerializedName("material_type_facet")
        var materialTypeFacet: String = "",
        @SerializedName("multimedia")
        var multimedia: List<Multimedia> = emptyList(),
        @SerializedName("published_date")
        var publishedDate: String = "",
        @SerializedName("section")
        var section: String = "",
        @SerializedName("short_url")
        var shortUrl: String = "",
        @SerializedName("subsection")
        var subsection: String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("updated_date")
        var updatedDate: String = "",
        @SerializedName("uri")
        var uri: String = "",
        @PrimaryKey
        @SerializedName("url")
        var url: String = "",
        @SerializedName("bookmarked")
        var bookmarked: Boolean = false
)

//    @SerializedName("des_facet")
//    var desFacet: List<String>,
//    @SerializedName("geo_facet")
//    var geoFacet: List<String>,
//@SerializedName("org_facet")
//    var orgFacet: List<String>,
//    @SerializedName("per_facet")
//    var perFacet: List<String>,