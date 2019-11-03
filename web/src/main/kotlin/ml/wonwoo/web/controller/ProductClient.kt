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


    fun books(name: String): Mono<List<Book>> =
        webClient
            .get()
            .uri("${productProperties.book}?name=$name")
            .retrieve()
            .bodyToMono()


    fun movies(name: String): Mono<List<Movie>> =
        webClient
            .get()
            .uri("${productProperties.movie}?name=$name")
            .retrieve()
            .bodyToMono()


    fun histories(): Flux<History> =
        webClient
            .get()
            .uri(productProperties.histories)
            .retrieve()
            .bodyToFlux()
}