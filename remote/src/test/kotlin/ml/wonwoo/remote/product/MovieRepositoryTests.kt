package ml.wonwoo.remote.product

import ml.wonwoo.remote.assertThatExceptionOfType
import ml.wonwoo.remote.eq
import ml.wonwoo.remote.expectCompleteAndVerify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier


@DataMongoTest
internal class MovieRepositoryTests(private val movieRepository: MovieRepository) {

    @Test
    fun `find by name test`() {

        movieRepository.save(Movie(name = "testmovie", image = "http://test.com/1", genre = "SF"))
            .block()

        val movies = movieRepository.findByName("testmovie")

        StepVerifier
            .create(movies)
            .assertNext {

                it.apply {

                    assertThat(name) eq "testmovie"
                    assertThat(image) eq "http://test.com/1"
                    assertThat(genre) eq "SF"

                }

            }
            .expectCompleteAndVerify()

    }


    @Test
    fun `find by name exception test`() {

        movieRepository.save(Movie(name = "testmovie1", image = "http://test.com/1", genre = "SF"))
            .block()

        val movies = movieRepository.findByName("testmovie2")

        assertThatExceptionOfType<AssertionError>()
            .isThrownBy {

                StepVerifier
                    .create(movies)
                    .expectError()
                    .verify()

            }

            .withMessage("""expectation "expectError()" failed (expected: onError(); actual: onComplete())""")
    }

}


