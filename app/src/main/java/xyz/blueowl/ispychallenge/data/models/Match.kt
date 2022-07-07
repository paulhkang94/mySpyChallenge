package xyz.blueowl.ispychallenge.data.models

/**
 * Data class that represents a match of a user who attempted a challenge.
 *
 * @property id: Challenge Id associated to the match.
 * @property latitude: Latitude coordinates of where the match took place
 * @property longitude: Longitude coordinates of where the match took place.
 * @property photoImageName: Image URL/URI of the challenge object.
 * @property verified: Boolean that determines if the user found the object. {@code true}  if the user won, otherwise {@code false}.
 * @property userId: Id of the user who attempted the match for challenge.
 *
 */
data class Match(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val photoImageName: String,
    val verified: Boolean,
    val userId: String
)
