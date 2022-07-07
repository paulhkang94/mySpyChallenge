package xyz.blueowl.ispychallenge.data.models

import java.net.URL

/**
 * Data class that represents information on a user.
 *
 * @property id: Unique identifier of the user.
 * @property email: Email address associated to the user.
 * @property username: Username associated to the user.
 * @property avatarLargeUrl: URL of the user avatar in large format.
 * @property avatarMediumUrl: URL of the user avatar in medium format.
 * @property avatarThumbnailUrl: URL of the user avatar in thumbnail format.
 * @property challenges: List of challenges the user created.
 *
 */
data class User(
    val id: String,
    val email: String,
    val username: String,
    val avatarLargeUrl: URL?,
    val avatarMediumUrl: URL?,
    val avatarThumbnailUrl: URL?,
    val challenges: List<Challenge>
)
