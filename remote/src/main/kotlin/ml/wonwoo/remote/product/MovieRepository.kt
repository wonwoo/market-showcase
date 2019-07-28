package ml.wonwoo.remote.product

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface MovieRepository : ReactiveMongoRepository<Movie, String> {

    fun findByName(name: String): Flux<Movie>

}