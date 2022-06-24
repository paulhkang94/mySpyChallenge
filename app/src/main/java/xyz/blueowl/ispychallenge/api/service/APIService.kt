package xyz.blueowl.ispychallenge.api.service

import xyz.blueowl.ispychallenge.api.models.APIChallenge
import xyz.blueowl.ispychallenge.api.models.APIUser

interface APIService {

    fun getUsers(completionCallback: (List<APIUser>)->Unit)

    fun getChallenges(completionCallback: (List<APIChallenge>)->Unit)
}
