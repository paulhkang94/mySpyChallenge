package xyz.blueowl.ispychallenge.ui.data_browser.challenges

import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.AttributesHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.HeaderLabelListItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.RelationshipHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.TextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.shared.HeaderItem

class ChallengeViewModel(
    challengeId: String,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val adapterItems = mutableListOf<AdapterItem>()
        dataRepository.getAllChallenges().firstOrNull { it.id == challengeId }?.let { challenge ->
            adapterItems.add(HeaderItem(AttributesHeaderItem()))
            adapterItems.add(HeaderLabelListItem(
                challenge.hint,
                "hint"
            ))
            adapterItems.add(HeaderLabelListItem(
                challenge.latitude.toString(),
                "latitude"
            ))
            adapterItems.add(HeaderLabelListItem(
                challenge.longitude.toString(),
                "longitude"
            ))
            adapterItems.add(HeaderLabelListItem(
                challenge.photoImageName,
                "photoHref"
            ))

            adapterItems.add(HeaderItem(RelationshipHeaderItem()))
            adapterItems.add(TextChevronItem("Creator"){
                _navigationFlow.tryEmit(DataBrowserNavState.UserNavState(challenge.userId))
            })
            adapterItems.add(TextChevronItem("Matches"){
                _navigationFlow.tryEmit(DataBrowserNavState.MatchesNavState(userId = null, challenge.id))
            })
            adapterItems.add(TextChevronItem("Ratings"){
                _navigationFlow.tryEmit(DataBrowserNavState.RatingsNavState(userId = null, challenge.id))
            })
        }

        _adapterItems.tryEmit(adapterItems)
    }
}
