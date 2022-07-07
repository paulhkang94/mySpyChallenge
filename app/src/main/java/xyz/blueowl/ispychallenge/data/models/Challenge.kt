package xyz.blueowl.ispychallenge.data.models

/**
 * Data class that represents the information related to a Challenge.
 *
 * @property id: The unique identifier for a challenge.
 * @property hint: The description that provides info of the object of the challenge.
 * @property latitude: The latitude coordinates of the object.
 * @property longitude: The longitude coordinates of the object.
 * @property photoImageName: The image URL/URI for the object.
 * @property userId: The unique identifier of the user who created the challenge.
 * @property matches: List of matches of users who attempted the challenge.
 * @property ratings: List of ratings of users who rated the challenge.
 */
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
