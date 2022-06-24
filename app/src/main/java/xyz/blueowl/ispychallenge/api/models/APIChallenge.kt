package xyz.blueowl.ispychallenge.api.models

data class APIChallenge(
    val id: String,
    val photo: String,
    val hint: String,
    val user: String,
    val location: APILocation,
    val matches: List<APIMatch>,
    val ratings: List<APIRating>
)
