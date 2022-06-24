package xyz.blueowl.ispychallenge.data.models

data class Match(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val photoImageName: String,
    val verified: Boolean,
    val userId: String
)
