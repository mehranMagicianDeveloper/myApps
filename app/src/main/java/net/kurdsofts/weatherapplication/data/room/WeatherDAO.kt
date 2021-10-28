package net.kurdsofts.weatherapplication.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import net.kurdsofts.weatherapplication.data.model.response_models.CurrentResponse

@Dao
interface WeatherDAO {

    //    Current Table
    @Insert
    suspend fun insertCurrentResponse(currentResponse: CurrentResponse): Long

    @Query("SELECT * FROM current_response")
    suspend fun getCurrentResponse(): CurrentResponse

    @Delete
    suspend fun deleteCurrentResponse(currentResponse: CurrentResponse)

}