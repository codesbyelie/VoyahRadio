package elie.voyah.radio.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import elie.voyah.radio.app.utils.DataError
import elie.voyah.radio.app.utils.EmptyResult
import elie.voyah.radio.app.utils.Result
import elie.voyah.radio.app.utils.map
import elie.voyah.radio.data.database.VoyahRadioDao
import elie.voyah.radio.data.mappers.toRadio
import elie.voyah.radio.data.mappers.toRadioEntity
import elie.voyah.radio.data.network.RemoteRadioDataSource
import elie.voyah.radio.domain.Radio
import elie.voyah.radio.domain.RadioRepository

class RadioRepositoryImpl(
    private val remoteRadioDataSource: RemoteRadioDataSource,
    private val voyahRadioDao: VoyahRadioDao
) : RadioRepository {
    override suspend fun searchRadios(query: String): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.searchRadio(query).map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }

    override suspend fun getRadios(
        offset: Int,
        limit: Int
    ): Result<List<Radio>, DataError.Remote> {
        return remoteRadioDataSource.fetchRadios(
            offset = offset,
            limit = limit
        ).map { responseDtoList ->
            responseDtoList.map { it.toRadio() }
        }
    }

    override suspend fun saveRadio(radio: Radio): EmptyResult<DataError.Local> {
        return try {
            voyahRadioDao.upsert(
                radio.toRadioEntity(
                    isSaved = true
                )
            )
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromSaved(id: String) {
        voyahRadioDao.deleteSavedRadio(id)
    }

    override fun getSavedRadios(): Flow<List<Radio>> {
        return voyahRadioDao.getSavedRadios().map { radioEntities ->
            radioEntities.sortedByDescending { it.timeStamp }.map { it.toRadio() }
        }
    }

    override suspend fun isSaved(id: String): Flow<Boolean> {
        return voyahRadioDao.isSaved(id)
    }

    override suspend fun insertRecentlyUpdatedRadios(radios: List<Radio>): EmptyResult<DataError.Local> {
        return try {
            voyahRadioDao.upsertRecentlyUpdatedRadios(radios.map { it.toRadioEntity() })
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getRecentlyUpdatedRadios(): Flow<List<Radio>> {
        return voyahRadioDao.getAllRadios().map { radioEntities ->
            radioEntities.map { it.toRadio() }
        }
    }
}