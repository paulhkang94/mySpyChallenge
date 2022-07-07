package xyz.blueowl.ispychallenge.api.service

import xyz.blueowl.ispychallenge.api.models.APIChallenge
import xyz.blueowl.ispychallenge.api.models.APIUser

/**
 * API Service that provides the necessary data related to the I SPY game.
 */
interface APIService {

    fun getUsers(completionCallback: (List<APIUser>)->Unit)

    fun getChallenges(completionCallback: (List<APIChallenge>)->Unit)
}
