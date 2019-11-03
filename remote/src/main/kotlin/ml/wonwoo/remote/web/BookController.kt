package ml.wonwoo.remote.web

import ml.wonwoo.remote.HistoryEvent
import ml.wonwoo.remote.product.Book
import ml.wonwoo.remote.product.BookRepository
import ml.wonwoo.remote.product.ProductType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/books")
class BookController(private val bookRepository: BookRepository,
                     private val applicationEventPublisher: ApplicationEventPublisher) {

    @GetMapping
    fun books(name: String): Flux<BookDto> {

        return bookRepository.findByName(name)

            .map { it.dto() }
            .doOnComplete {
                applicationEventPublisher.publishEvent(HistoryEvent(name = name, productType = ProductType.MOVIE))
            }
    }
}


data class BookDto(

    val id: String,

    val name: String,

    val image: String,

    val author: String
)

fun Book.dto() = BookDto(id, name, image, author)
