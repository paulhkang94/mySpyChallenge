package xyz.blueowl.ispychallenge.data.repository

import xyz.blueowl.ispychallenge.api.service.APIService
import xyz.blueowl.ispychallenge.data.models.*
import xyz.blueowl.ispychallenge.extensions.User

class LocalDataRepository(
    private val apiService: APIService
): DataRepository {

    private val currentUserIndex = 0
    override val allUsers: MutableList<User> = mutableListOf()

    init {
        apiService.getUsers { apiUsers ->
            apiService.getChallenges { apiChallenges ->
                allUsers.addAll(
                    apiUsers.map { User(it, apiChallenges) }
                )
            }
        }
    }

    override fun getUserByUserId(userId: String): User? {
        return allUsers.firstOrNull { it.id == userId }
    }

    override fun getAllChallenges(): List<Challenge> {
        return allUsers.flatMap { it.challenges }
    }

    override fun getChallengeForMatch(match: Match): Challenge? {
        return getAllChallenges().firstOrNull { it.matches.contains(match) }
    }

    override fun getAllMatches(): List<Match> {
        return getAllChallenges().flatMap { it.matches }
    }

    override fun getMatchesByUserId(userId: String): List<Match> {
        return getAllChallenges()
            .flatMap { it.matches }
            .filter { it.userId == userId }
    }

    override fun getRatingsByUserId(userId: String): List<Rating> {
        return getAllChallenges()
            .flatMap { it.ratings }
            .filter { it.userId == userId }
    }

    override fun getAssociatedUsersByRatings(ratings: List<Rating>): List<RatingAndAssociatedUser> {
        return ratings.mapNotNull { rating ->
            val user = getUserByUserId(rating.userId)
            if (user == null) {
                null
            } else {
                RatingAndAssociatedUser(rating, user)
            }
        }
    }
}