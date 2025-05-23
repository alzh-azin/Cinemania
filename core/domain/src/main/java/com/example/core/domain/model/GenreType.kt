package com.example.core.domain.model

enum class GenreType(
    val genreName: String,
    val movieCode: Int? = null,
    val tvShowCode: Int? = null
) {

    //TODO move movieCode & tvShowCode to GenreTypeEntity

    Comedy(genreName = "Comedy", movieCode = 35, tvShowCode = 35),
    Action(genreName = "Action", movieCode = 28, tvShowCode = 28),
    Adventure(genreName = "Adventure", movieCode = 12, tvShowCode = 10759),
    Animation(genreName = "Animation", movieCode = 16, tvShowCode = 16),
    Crime(genreName = "Crime", movieCode = 80, tvShowCode = 80),
    Documentary(genreName = "Documentary", movieCode = 99, tvShowCode = 99),
    Drama(genreName = "Drama", movieCode = 18, tvShowCode = 18),
    Family(genreName = "Family", movieCode = 10751, tvShowCode = 10751),
    Kids(genreName = "Kids", movieCode = null, tvShowCode = 10762),
    Fantasy(genreName = "Fantasy", movieCode = 14, tvShowCode = 10765),
    History(genreName = "History", movieCode = 36, tvShowCode = null),
    Horror(genreName = "Horror", movieCode = 27, tvShowCode = null),
    Music(genreName = "Music", movieCode = 10402, tvShowCode = null),
    Mystery(genreName = "Mystery", movieCode = 9648, tvShowCode = 9648),
    Romance(genreName = "Romance", movieCode = 10749, tvShowCode = null),
    ScienceFiction(genreName = "Sci-Fi", movieCode = 878, tvShowCode = 10765),
    TVMovie(genreName = "TV Movie", movieCode = 10770, tvShowCode = null),
    Thriller(genreName = "Thriller", movieCode = 53, tvShowCode = null),
    War(genreName = "War", movieCode = 10752, tvShowCode = 10768),
    Western(genreName = "Western", movieCode = 37, tvShowCode = 37),
    News(genreName = "News", movieCode = null, tvShowCode = 10763),
    Reality(genreName = "Reality", movieCode = null, tvShowCode = 10764),
    Soap(genreName = "Soap", movieCode = null, tvShowCode = 10766),
    Talk(genreName = "Talk", movieCode = null, tvShowCode = 10767);

    companion object {

        fun enumValueOf(genreCode: Int): String {

            enumValues<GenreType>().forEach {
                if (genreCode == it.movieCode || genreCode == it.tvShowCode)
                    return it.genreName
            }
            return ""
        }

        fun getGenreCode(name: String, mediaType: MediaType?): Int {
            enumValues<GenreType>().forEach {
                if (it.genreName == name) {
                    return when (mediaType) {
                        MediaType.MOVIE -> it.movieCode ?: -1
                        MediaType.TV_SHOW -> it.tvShowCode ?: -1
                        null -> -1
                    }
                }
            }
            return -1
        }


        fun generateGenreList(): List<GenreType> {

            return enumValues<GenreType>().toList()
        }

        val DEFAULT_GENRE = Comedy

    }
}