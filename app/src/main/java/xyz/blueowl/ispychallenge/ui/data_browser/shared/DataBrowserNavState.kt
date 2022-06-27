package xyz.blueowl.ispychallenge.ui.data_browser.shared

sealed class DataBrowserNavState {

    class UserNavState(val userId: String): DataBrowserNavState()
    class ChallengesNavState(val userId: String): DataBrowserNavState()
    class ChallengeNavState(val challengeId: String): DataBrowserNavState()
    class MatchesNavState(val userId: String?, val challengeId: String?): DataBrowserNavState()
    class MatchNavState(val matchId: String): DataBrowserNavState()
    class RatingsNavState(val userId: String?, val challengeId: String?): DataBrowserNavState()
}
