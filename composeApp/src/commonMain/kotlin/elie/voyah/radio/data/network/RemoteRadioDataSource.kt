package elie.voyah.radio.data.network

import elie.voyah.radio.data.dto.RadioSearchResponseDto
import elie.voyah.radio.app.utils.DataError
import elie.voyah.radio.app.utils.Result

interface RemoteRadioDataSource {
    suspend fun searchRadio(
        query: String,
        resultLimit: Int? = 50
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>

    suspend fun fetchRadios(
        offset: Int,
        limit: Int
    ): Result<List<RadioSearchResponseDto>, DataError.Remote>
}