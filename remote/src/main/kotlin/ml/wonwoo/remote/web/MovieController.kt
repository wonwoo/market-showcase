package ml.wonwoo.remote.web

import ml.wonwoo.remote.HistoryEvent
import ml.wonwoo.remote.product.Movie
import ml.wonwoo.remote.product.MovieRepository
import ml.wonwoo.remote.product.ProductType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/movies")
class MovieController(private val movieRepository: MovieRepository,
                      private val applicationEventPublisher: ApplicationEventPublisher) {

    @GetMapping
    fun movies(name: String): Flux<MovieDto> {

        return movieRepository.findByName(name)

            .map { it.dto() }

            .doOnComplete {

                applicationEventPublisher.publishEvent(HistoryEvent(name = name, productType = ProductType.MOVIE))

            }
    }

}

data class MovieDto(

    val id: String,

    val name: String,

    val image: String,

    val genre: String
)

fun Movie.dto() = MovieDto(id, name, image, genre)
