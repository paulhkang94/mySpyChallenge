package xyz.blueowl.ispychallenge.ui.data_browser.matches

import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.AttributesHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.HeaderLabelListItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.RelationshipHeaderItem
import xyz.blueowl.ispychallenge.ui.data_browser.adapter_items.TextChevronItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.AdapterItem
import xyz.blueowl.ispychallenge.ui.data_browser.shared.BaseDataBrowserViewModel
import xyz.blueowl.ispychallenge.ui.data_browser.shared.DataBrowserNavState
import xyz.blueowl.ispychallenge.ui.data_browser.shared.HeaderItem

class MatchViewModel(
    matchId: String,
    dataRepository: DataRepository
): BaseDataBrowserViewModel() {

    init {
        val adapterItems = mutableListOf<AdapterItem>()
        dataRepository.getAllMatches().firstOrNull() { it.id == matchId }?.let { match ->
            adapterItems.add(HeaderItem(AttributesHeaderItem()))
            adapterItems.add(HeaderLabelListItem(
                match.latitude.toString(),
                "latitude"
            ))
            adapterItems.add(HeaderLabelListItem(
                match.longitude.toString(),
                "longitude"
            ))
            adapterItems.add(HeaderLabelListItem(
                match.photoImageName,
                "photoHref"
            ))
            adapterItems.add(HeaderLabelListItem(
                match.verified.toString(),
                "verified"
            ))

            adapterItems.add(HeaderItem(RelationshipHeaderItem()))
            adapterItems.add(TextChevronItem("Challenge") {
                dataRepository.getChallengeForMatch(match)?.let {
                    _navigationFlow.tryEmit(DataBrowserNavState.ChallengeNavState(it.id))
                }
            })
            adapterItems.add(TextChevronItem("User") {
                _navigationFlow.tryEmit(DataBrowserNavState.UserNavState(match.userId))
            })
        }

        _adapterItems.tryEmit(adapterItems)
    }
}