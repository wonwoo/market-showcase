package ml.wonwoo.remote.web

import ml.wonwoo.remote.product.Movie
import ml.wonwoo.remote.product.MovieRepository
import ml.wonwoo.remote.sleep
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/movies")
class MovieController(private val movieRepository: MovieRepository) {

    @GetMapping
    fun movies() = sleep(3000) {

        movieRepository.findAll()

            .map { it.dto() }

    }

}

data class MovieDto(

    val id: String,

    val name: String,

    val image: String,

    val genre: String
)

fun Movie.dto() = MovieDto(id, name, image, genre)
