package elie.voyah.radio.domain

import kotlinx.coroutines.flow.Flow
import elie.voyah.radio.app.utils.DataError
import elie.voyah.radio.app.utils.EmptyResult
import elie.voyah.radio.app.utils.Result

interface RadioRepository {
    suspend fun searchRadios(query: String): Result<List<Radio>, DataError.Remote>
    suspend fun getRadios(offset: Int, limit: Int): Result<List<Radio>, DataError.Remote>
    suspend fun saveRadio(radio: Radio): EmptyResult<DataError.Local>
    suspend fun deleteFromSaved(id: String)
    suspend fun isSaved(id: String): Flow<Boolean>
    suspend fun insertRecentlyUpdatedRadios(radios: List<Radio>): EmptyResult<DataError.Local>
    fun getRecentlyUpdatedRadios(): Flow<List<Radio>>
    fun getSavedRadios(): Flow<List<Radio>>
}