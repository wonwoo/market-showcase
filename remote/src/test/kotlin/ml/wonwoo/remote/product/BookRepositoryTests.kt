package ml.wonwoo.remote.product

import ml.wonwoo.remote.assertThatExceptionOfType
import ml.wonwoo.remote.eq
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.kotlin.test.test

@DataMongoTest
internal class BookRepositoryTests(private val bookRepository: BookRepository) {

    @Test
    fun `find by name test`() {


        val books = bookRepository.save(Book(name = "testbook", image = "http://test.com", author = "wonwoo"))
            .thenMany(bookRepository.findByName("testbook"))

        books.test()
            .assertNext {

                it.apply {

                    assertThat(name) eq "testbook"
                    assertThat(image) eq "http://test.com"
                    assertThat(author) eq "wonwoo"

                }

            }
            .verifyComplete()

    }

    @Test
    fun `find by name exception test`() {

        val books = bookRepository.save(Book(name = "testbook1", image = "http://test.com", author = "wonwoo"))
            .thenMany(bookRepository.findByName("testbook2"))

        assertThatExceptionOfType<AssertionError>()
            .isThrownBy {

                books.test()
                    .expectError()
                    .verify()

            }

            .withMessage("""expectation "expectError()" failed (expected: onError(); actual: onComplete())""")
    }

}


