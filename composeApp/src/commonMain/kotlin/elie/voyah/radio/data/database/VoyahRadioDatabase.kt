package elie.voyah.radio.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        RadioEntity::class
    ],
    version = 1
)

@TypeConverters(TypeConverter::class)

@ConstructedBy(BookDatabaseConstructor::class)

abstract class VoyahRadioDatabase : RoomDatabase() {
    abstract val voyahRadioDao: VoyahRadioDao

    companion object {
        const val VOYAH_RADIO_DB_NAME = "VoyahRadio.db"
    }

    suspend fun clearAllEntities() {
        voyahRadioDao.clearAll()
    }
}