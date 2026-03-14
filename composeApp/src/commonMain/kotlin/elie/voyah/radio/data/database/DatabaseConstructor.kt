package elie.voyah.radio.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<VoyahRadioDatabase> {
    override fun initialize(): VoyahRadioDatabase
}