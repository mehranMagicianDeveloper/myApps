package net.kurdsofts.weatherapplication.data.model.response_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.models.Location
import net.kurdsofts.weatherapplication.data.room.CurrentConvertor
import javax.inject.Inject

@Entity(tableName = "current_response")
data class CurrentResponse @Inject constructor(
    @PrimaryKey(autoGenerate = false)
    val location: Location,
    val current: Current
)