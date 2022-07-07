package xyz.blueowl.ispychallenge.data.repository

import xyz.blueowl.ispychallenge.data.models.*

/**
 * Repository that provides all necessary data related to the I Spy game.
 */
interface DataRepository {

    val allUsers: List<User>

    fun getUserByUserId(userId: String): User?

    fun getAllChallenges(): List<Challenge>

    fun getChallengeForMatch(match: Match): Challenge?

    fun getAllMatches(): List<Match>

    fun getMatchesByUserId(userId: String): List<Match>

    fun getRatingsByUserId(userId: String): List<Rating>

    fun getAssociatedUsersByRatings(ratings: List<Rating>): List<RatingAndAssociatedUser>
}
