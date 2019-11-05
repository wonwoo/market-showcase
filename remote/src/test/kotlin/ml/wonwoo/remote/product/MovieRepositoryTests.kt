package ml.wonwoo.remote.product

import ml.wonwoo.remote.assertThatExceptionOfType
import ml.wonwoo.remote.eq
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.kotlin.test.test


@DataMongoTest
internal class MovieRepositoryTests(private val movieRepository: MovieRepository) {

    @Test
    fun `find by name test`() {

        val movies = movieRepository.save(Movie(name = "testmovie", image = "http://test.com/1", genre = "SF"))
            .thenMany(movieRepository.findByName("testmovie"))

        movies.test()
            .assertNext {

                it.apply {

                    assertThat(name) eq "testmovie"
                    assertThat(image) eq "http://test.com/1"
                    assertThat(genre) eq "SF"

                }

            }
            .verifyComplete()

    }


    @Test
    fun `find by name exception test`() {

        val movies = movieRepository.save(Movie(name = "testmovie1", image = "http://test.com/1", genre = "SF"))
            .thenMany(movieRepository.findByName("testmovie2"))

        assertThatExceptionOfType<AssertionError>()
            .isThrownBy {

                movies.test()
                    .expectError()
                    .verify()

            }

            .withMessage("""expectation "expectError()" failed (expected: onError(); actual: onComplete())""")
    }

}


