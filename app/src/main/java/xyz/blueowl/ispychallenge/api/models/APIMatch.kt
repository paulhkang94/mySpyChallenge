package xyz.blueowl.ispychallenge.api.models

data class APIMatch(
    val id: String,
    val location: APILocation,
    val photo: String,
    val verified: Boolean,
    val user: String
)
