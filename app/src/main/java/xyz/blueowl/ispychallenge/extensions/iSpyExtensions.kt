package xyz.blueowl.ispychallenge.extensions

import xyz.blueowl.ispychallenge.api.models.APIChallenge
import xyz.blueowl.ispychallenge.api.models.APIMatch
import xyz.blueowl.ispychallenge.api.models.APIRating
import xyz.blueowl.ispychallenge.api.models.APIUser
import xyz.blueowl.ispychallenge.data.models.User
import xyz.blueowl.ispychallenge.data.models.Challenge
import xyz.blueowl.ispychallenge.data.models.Match
import xyz.blueowl.ispychallenge.data.models.Rating
import java.net.URL

fun User(apiUser: APIUser, apiChallenge: List<APIChallenge>) = User(
    id = apiUser.id,
    email = apiUser.email,
    username = apiUser.username,
    avatarLargeUrl = URL(apiUser.picture.large),
    avatarMediumUrl = URL(apiUser.picture.medium),
    avatarThumbnailUrl = URL(apiUser.picture.thumbnail),
    challenges = apiChallenge
        .filter { it.user == apiUser.id }
        .map { Challenge(it) }
)

fun Challenge(apiChallenge: APIChallenge) = Challenge(
    id = apiChallenge.id,
    hint = apiChallenge.hint,
    latitude = apiChallenge.location.latitude,
    longitude = apiChallenge.location.longitude,
    photoImageName = "file:///android_asset/${apiChallenge.photo}",
    userId = apiChallenge.user,
    matches = apiChallenge.matches.map { Match(it) },
    ratings = apiChallenge.ratings.map { Rating(it) }
)

fun Match(apiMatch: APIMatch) = Match(
    id = apiMatch.id,
    latitude = apiMatch.location.latitude,
    longitude = apiMatch.location.longitude,
    photoImageName = "file:///android_asset/${apiMatch.photo}",
    verified = apiMatch.verified,
    userId = apiMatch.user
)

fun Rating(apiRating: APIRating) = Rating(
    id = apiRating.id,
    stars = apiRating.value,
    userId = apiRating.user
)
