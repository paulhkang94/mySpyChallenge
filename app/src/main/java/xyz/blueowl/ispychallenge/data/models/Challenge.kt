package xyz.blueowl.ispychallenge.data.models

data class Challenge (
    val id: String,
    val hint: String,
    val latitude: Double,
    val longitude: Double,
    val photoImageName: String,
    val userId: String,
    val matches: List<Match>,
    val ratings: List<Rating>
)
