package xyz.blueowl.ispychallenge

import android.app.Application
import xyz.blueowl.ispychallenge.api.service.APIService
import xyz.blueowl.ispychallenge.api.service.MockAPIService
import xyz.blueowl.ispychallenge.data.repository.DataRepository
import xyz.blueowl.ispychallenge.data.repository.LocalDataRepository
import java.io.InputStreamReader

class ISpyApplication: Application() {

    lateinit var apiService: APIService

    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()

        apiService = MockAPIService(
            InputStreamReader(assets.open("users.json")),
            InputStreamReader(assets.open("challenges.json")))

        dataRepository = LocalDataRepository(apiService)
    }
}
