package ml.wonwoo.remote.product

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux

interface HistoryRepository : ReactiveMongoRepository<History, String> {

    @Tailable
    fun findBy(): Flux<History>

}