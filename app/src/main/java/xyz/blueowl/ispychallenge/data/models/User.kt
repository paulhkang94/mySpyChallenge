package xyz.blueowl.ispychallenge.data.models

import java.net.URL

data class User(
    val id: String,
    val email: String,
    val username: String,
    val avatarLargeUrl: URL?,
    val avatarMediumUrl: URL?,
    val avatarThumbnailUrl: URL?,
    val challenges: List<Challenge>
)
