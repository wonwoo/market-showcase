package ml.wonwoo.web.controller

import ml.wonwoo.web.ProductProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductClient(private val productProperties: ProductProperties,
                    builder: WebClient.Builder) {

    private val webClient: WebClient = builder.baseUrl(productProperties.host).build()


    fun books(): Mono<List<Book>> =
        webClient
            .get()
            .uri(productProperties.book)
            .retrieve()
            .bodyToMono()


    fun movies(): Mono<List<Movie>> =
        webClient
            .get()
            .uri(productProperties.movie)
            .retrieve()
            .bodyToMono()


    fun histories(): Flux<History> =
        webClient
            .get()
            .uri(productProperties.histories)
            .retrieve()
            .bodyToFlux()
}