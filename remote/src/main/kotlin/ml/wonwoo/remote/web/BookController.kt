package ml.wonwoo.remote.web

import ml.wonwoo.remote.product.Book
import ml.wonwoo.remote.product.BookRepository
import ml.wonwoo.remote.product.History
import ml.wonwoo.remote.product.HistoryRepository
import ml.wonwoo.remote.product.ProductType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@RestController
@RequestMapping("/books")
class BookController(private val bookRepository: BookRepository, private val historyRepository: HistoryRepository) {

    @GetMapping
    fun books(name: String): Flux<BookDto> {
        val books = bookRepository.findByName(name)

            .map { it.dto() }

        val history = historyRepository.save(
            History(name = name, productType = ProductType.BOOK, registerTime = LocalDateTime.now()))

        return Flux.zip(books, history)

            .map { it.t1 }

    }
}


data class BookDto(

    val id: String,

    val name: String,

    val image: String,

    val author: String
)

fun Book.dto() = BookDto(id, name, image, author)
