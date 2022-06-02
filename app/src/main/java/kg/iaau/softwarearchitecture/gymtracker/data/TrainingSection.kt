package kg.iaau.softwarearchitecture.gymtracker.data

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "training_category")
class TrainingSection(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @IdRes val image: Int,
    val title: String,
    val subtitle: String,
    val gender: String? = null
): Parcelable