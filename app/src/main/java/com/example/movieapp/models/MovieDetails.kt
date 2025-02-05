package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("revenue") var revenue: Int? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("budget") var budget: Int? = null,
    @SerializedName("homepage") var homepage: String? = null,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("genres") var genres: String? = null,
    @SerializedName("production_companies") var productionCompanies: String? = null,
    @SerializedName("production_countries") var productionCountries: String? = null,
    @SerializedName("spoken_languages") var spokenLanguages: String? = null
)
