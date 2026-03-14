package elie.voyah.radio.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<VoyahRadioDatabase>
}