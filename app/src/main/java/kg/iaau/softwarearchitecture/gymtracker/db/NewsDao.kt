package kg.iaau.softwarearchitecture.gymtracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kg.iaau.softwarearchitecture.gymtracker.data.News
import java.util.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(list: List<News>)

    @Query("SELECT * FROM news WHERE id LIKE :id")
    fun getNews(id: UUID): News

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

}