package xyz.blueowl.ispychallenge.ui.near_me

import android.location.Location
import xyz.blueowl.ispychallenge.data.models.Challenge
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.NearMeChallengeItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState

class NearMeViewModel(
    originLatitude: Double,
    originLongitude: Double,
    dataRepository: DataRepository
) : BaseDataBrowserViewModel() {

    init {
        val adapterItems = dataRepository.getAllChallenges()
            .map {
                val username: String = dataRepository.getUserByUserId(it.userId)?.username ?: "unknown"

                it.toNearMeChallengeItem(originLatitude, originLongitude, username)
            }
            .sortedBy { it.distance }

        _adapterItems.tryEmit(adapterItems)
    }

    private fun Challenge.toNearMeChallengeItem(
        originLatitude: Double,
        originLongitude: Double,
        username: String,
    ): NearMeChallengeItem {
        val distances = FloatArray(3)

        Location.distanceBetween(
            originLatitude,
            originLongitude,
            this.latitude,
            this.longitude,
            distances
        )

        return NearMeChallengeItem(
            challengeId = this.id,
            winsCount = this.matches.count { it.verified },
            avgRating = this.ratings.sumOf { it.stars }.toDouble() / this.ratings.size,
            distance = distances[0].toDouble(),
            hint = this.hint,
            creator = username
        ) {
            onChallengeClick(this.id)
        }
    }

    private fun onChallengeClick(challengeId: String) {
        _navigationFlow.tryEmit(DataBrowserNavState.ChallengeImageState(challengeId))
    }
}