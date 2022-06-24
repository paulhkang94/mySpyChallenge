package xyz.blueowl.ispychallenge.api.models

data class APIUser(
    val id: String,
    val email: String,
    val username: String,
    val picture: Picture) {

    data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
    )
}
