package ml.wonwoo.remote.web

import ml.wonwoo.remote.product.Book
import ml.wonwoo.remote.product.BookRepository
import ml.wonwoo.remote.sleep
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookRepository: BookRepository) {

    @GetMapping
    fun books() = sleep {

        bookRepository.findAll()

            .map { it.dto() }

    }

}

data class BookDto(

    val id: String,

    val name: String,

    val image: String,

    val author: String
)

fun Book.dto() = BookDto(id, name, image, author)
