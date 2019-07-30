package ml.wonwoo.remote.product

import ml.wonwoo.remote.assertThatExceptionOfType
import ml.wonwoo.remote.eq
import ml.wonwoo.remote.expectCompleteAndVerify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
internal class BookRepositoryTests(private val bookRepository: BookRepository) {

    @Test
    fun `find by name test`() {

        bookRepository.save(Book(name = "testbook", image = "http://test.com", author = "wonwoo"))
            .block()

        val books = bookRepository.findByName("testbook")

        StepVerifier
            .create(books)
            .assertNext {

                it.apply {

                    assertThat(name) eq "testbook"
                    assertThat(image) eq "http://test.com"
                    assertThat(author) eq "wonwoo"

                }

            }
            .expectCompleteAndVerify()

    }

    @Test
    fun `find by name exception test`() {

        bookRepository.save(Book(name = "testbook1", image = "http://test.com", author = "wonwoo"))
            .block()

        val books = bookRepository.findByName("testbook2")

        assertThatExceptionOfType<AssertionError>()
            .isThrownBy {

                StepVerifier
                    .create(books)
                    .expectError()
                    .verify()

            }

            .withMessage("""expectation "expectError()" failed (expected: onError(); actual: onComplete())""")
    }

}


