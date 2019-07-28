package ml.wonwoo.remote.web

import ml.wonwoo.remote.product.History
import ml.wonwoo.remote.product.HistoryRepository
import ml.wonwoo.remote.product.Movie
import ml.wonwoo.remote.product.MovieRepository
import ml.wonwoo.remote.product.ProductType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@RestController
@RequestMapping("/movies")
class MovieController(private val movieRepository: MovieRepository,
                      private val historyRepository: HistoryRepository) {

    @GetMapping
    fun movies(name: String): Flux<MovieDto> {
        val movies = movieRepository.findByName(name)

            .map { it.dto() }

        val history = historyRepository.save(
            History(name = name, productType = ProductType.MOVIE, registerTime = LocalDateTime.now()))

        return Flux.zip(movies, history)

            .map { it.t1 }
    }

}

data class MovieDto(

    val id: String,

    val name: String,

    val image: String,

    val genre: String
)

fun Movie.dto() = MovieDto(id, name, image, genre)
