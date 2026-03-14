package elie.voyah.radio.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import elie.voyah.radio.app.utils.DataError
import elie.voyah.radio.app.utils.RADIO_BROWSER_BASE_URL_SERVER1
import elie.voyah.radio.app.utils.RADIO_BROWSER_BASE_URL_SERVER2
import elie.voyah.radio.app.utils.RADIO_BROWSER_BASE_URL_SERVER3
import elie.voyah.radio.app.utils.Result
import elie.voyah.radio.app.utils.USER_AGENT
import elie.voyah.radio.app.utils.safeCall
import elie.voyah.radio.data.dto.RadioSearchResponseDto

class RemoteRadioDataSourceImpl(
    private val httpClient: HttpClient,
) : RemoteRadioDataSource {

    private val radioBrowserBaseUrls = listOf(
        RADIO_BROWSER_BASE_URL_SERVER1,
        RADIO_BROWSER_BASE_URL_SERVER2,
        RADIO_BROWSER_BASE_URL_SERVER3,
    )

    override suspend fun searchRadio(
        query: String,
        resultLimit: Int?
    ): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return fetchFromMirrors { baseUrl ->
            safeCall<List<RadioSearchResponseDto>> {
                httpClient.get("$baseUrl/stations/search") {
                    header("User-Agent", USER_AGENT)
                    parameter("name", query)
                    parameter("limit", resultLimit)
                    parameter("countrycode", "LB")
                    parameter("hidebroken", "true")
                    parameter("order", "clickcount")
                }
            }
        }
    }

    override suspend fun fetchRadios(
        offset: Int,
        limit: Int
    ): Result<List<RadioSearchResponseDto>, DataError.Remote> {
        return fetchFromMirrors { baseUrl ->
            safeCall<List<RadioSearchResponseDto>> {
                httpClient.get("$baseUrl/stations/search") {
                    header("User-Agent", USER_AGENT)
                    parameter("limit", limit)
                    parameter("offset", offset)
                    parameter("countrycode", "LB")
                    parameter("hidebroken", "true")
                    parameter("order", "clickcount")
                    parameter("reverse", "true")
                }
            }
        }
    }

    private suspend fun <T> fetchFromMirrors(
        attempt: suspend (String) -> Result<T, DataError.Remote>
    ): Result<T, DataError.Remote> {
        val errors = mutableListOf<DataError.Remote>()
        for (baseUrl in radioBrowserBaseUrls) {
            when (val result = attempt(baseUrl)) {
                is Result.Success -> return result
                is Result.Error -> errors.add(result.error)
            }
        }

        return Result.Error(errors.lastOrNull() ?: DataError.Remote.UNKNOWN)
    }
}