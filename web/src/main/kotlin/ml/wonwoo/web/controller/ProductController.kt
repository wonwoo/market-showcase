package ml.wonwoo.web.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
class ProductController(private val productClient: ProductClient) {

    private val histories =  productClient.histories()

    @GetMapping("/")
    fun products(): Mono<Product> {

        val books = productClient.books()

        val movies = productClient.movies()

        return Mono.zip(books, movies)
            .map { Product(it.t1, it.t2) }

    }

    @GetMapping(value = ["/histories"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun histories(): Flux<History> = histories
}


data class Product(

    val books: List<Book>,

    val movies: List<Movie>

)