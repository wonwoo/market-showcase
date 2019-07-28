package ml.wonwoo.remote.web

import ml.wonwoo.remote.product.HistoryRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/histories")
class HistoryController(private val historyRepository: HistoryRepository) {

    @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun historiesEvent() = historyRepository.findBy()

}
