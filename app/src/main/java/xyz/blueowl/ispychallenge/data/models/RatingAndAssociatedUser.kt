package xyz.blueowl.ispychallenge.data.models

/**
 * Data class that represents the association of a rating to user.
 *
 * @property rating: The rating the user gave.
 * @property user: The user owner of the rating.
 *
 */
data class RatingAndAssociatedUser(
    val rating: Rating,
    val user: User
)
