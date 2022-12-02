package xyz.blueowl.ispychallenge.data.models

/**
 * Data class that represents the rating a user gave to a challenge.
 *
 * @property id: Challenge Id the rating is associated to.
 * @property stars: The number of stars the user gave the challenge. Ranges from 1 to 5.
 * @property userId: User Id of user who gave the rating for the challenge.
 *
 */
data class Rating(
    val id: String,
    val stars: Int,
    val userId: String
)

