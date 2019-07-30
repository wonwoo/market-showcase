package ml.wonwoo.remote.web

import ml.wonwoo.remote.any
import ml.wonwoo.remote.eq
import ml.wonwoo.remote.product.Book
import ml.wonwoo.remote.product.BookRepository
import ml.wonwoo.remote.product.History
import ml.wonwoo.remote.product.HistoryRepository
import ml.wonwoo.remote.product.ProductType
import org.assertj.core.api.Assertions.assertThat
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
import java.time.LocalDateTime
import java.util.UUID


@WebFluxTest(BookController::class)
internal class BookControllerTests(private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var bookRepository: BookRepository

    @MockBean
    private lateinit var historyRepository: HistoryRepository


    @BeforeEach
    fun setup() {
        val history = History(name = "book", productType = ProductType.BOOK, registerTime = LocalDateTime.now())
        given(historyRepository.save(any<History>()))
            .willReturn(Mono.just(history))
    }

    @Test
    fun `books test`() {

        given(bookRepository.findByName("book"))
            .willReturn(Flux.just(Book(id = UUID.randomUUID().toString(), name = "testbook", image = "http://test.com", author = "wonwoo")))

        webTestClient.get().uri {

            it.path("/books")
                .queryParam("name", "book")
                .build()

        }.accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBodyList<BookDto>()
            .hasSize(1)
            .consumeWith<WebTestClient.ListBodySpec<BookDto>> {

                val body = it.responseBody!!

                assertThat(body[0].name) eq "testbook"
                assertThat(body[0].image) eq "http://test.com"
                assertThat(body[0].author) eq "wonwoo"
            }

    }

}
