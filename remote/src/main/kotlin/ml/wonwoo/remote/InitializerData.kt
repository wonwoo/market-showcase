package ml.wonwoo.remote


import ml.wonwoo.remote.product.Book
import ml.wonwoo.remote.product.BookRepository
import ml.wonwoo.remote.product.Movie
import ml.wonwoo.remote.product.MovieRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class InitializerData(

    private val bookRepository: BookRepository,
    private val movieRepository: MovieRepository

) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        bookRepository.save(Book(name = "Effective Java", image = "http://localhost:8080", author = "Joshua Bloch"))

            .then(bookRepository.save(Book(name = "Back to the Future", image = "http://localhost:8080", author = "Michael Klastorin")))

            .then(movieRepository.save(Movie(name = "Back to the Future", image = "http://localhost:8080", genre = "SF")))

            .subscribe()

    }

}