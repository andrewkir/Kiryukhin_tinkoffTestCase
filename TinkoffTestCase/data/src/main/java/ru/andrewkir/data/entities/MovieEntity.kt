package ru.andrewkir.data.entities

import androidx.room.*
import java.util.*
import java.util.stream.Collectors

@Entity(tableName = "MovieEntity")
data class MovieEntity(
    @PrimaryKey val filmId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "posterUrl") val posterUrl: String,
    @TypeConverters(GenresConverter::class)
    @ColumnInfo(name = "genres") val genres: List<String>,
    @TypeConverters(GenresConverter::class)
    @ColumnInfo(name = "countries") val countries: List<String>,
    @ColumnInfo(name = "description") val description: String
)

class GenresConverter {
    @TypeConverter
    fun fromGenres(genres: List<String?>): String {
        return genres.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toGenres(data: String): List<String> {
        return data.split(",".toRegex())
    }
}