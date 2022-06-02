package kg.iaau.softwarearchitecture.gymtracker.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class GymTypeConverter {

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

}