package ml.wonwoo.remote.web

import ml.wonwoo.remote.any
import ml.wonwoo.remote.eq
import ml.wonwoo.remote.product.History
import ml.wonwoo.remote.product.HistoryRepository
import ml.wonwoo.remote.product.Movie
import ml.wonwoo.remote.product.MovieRepository
import ml.wonwoo.remote.product.ProductType
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.util.UUID


@WebFluxTest(MovieController::class)
internal class MovieControllerTests(private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var movieRepository: MovieRepository

    @MockBean
    private lateinit var historyRepository: HistoryRepository


    @BeforeEach
    fun setup() {
        val history = History(name = "book", productType = ProductType.BOOK, registerTime = LocalDateTime.now())
        given(historyRepository.save(any<History>()))
            .willReturn(history.toMono())
    }

    @Test
    fun `movies test`() {

        given(movieRepository.findByName("movie"))
            .willReturn(listOf(Movie(id = UUID.randomUUID().toString(), name = "testmovie", image = "http://test.com/1", genre = "SF")).toFlux())

        webTestClient.get().uri {

            it.path("/movies")
                .queryParam("name", "movie")
                .build()

        }.accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList<MovieDto>()
            .hasSize(1)
            .consumeWith<WebTestClient.ListBodySpec<MovieDto>> {

                val body = it.responseBody!!

                assertThat(body[0].name) eq "testmovie"
                assertThat(body[0].image) eq "http://test.com/1"
                assertThat(body[0].genre) eq "SF"
            }

    }

}
