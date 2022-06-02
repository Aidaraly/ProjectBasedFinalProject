package kg.iaau.softwarearchitecture.gymtracker.data

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "training")
data class Training(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @IdRes val image: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val time: Long,
    val category: String,
    var isFavourite: Boolean = false,
    var isRecommended: Boolean = false,
    val gender: String? = null
)