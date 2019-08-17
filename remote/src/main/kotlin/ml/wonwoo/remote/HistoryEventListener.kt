package ml.wonwoo.remote

import ml.wonwoo.remote.product.History
import ml.wonwoo.remote.product.HistoryRepository
import ml.wonwoo.remote.product.ProductType
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class HistoryEventListener(private val historyRepository: HistoryRepository) {


    @EventListener
    fun onApplicationEvent(historyEvent: HistoryEvent) {

        historyRepository.save(historyEvent.toEvent())

            .subscribe()
    }

}

data class HistoryEvent(

    val name: String,

    val productType: ProductType

)

fun HistoryEvent.toEvent() = History(name = this.name, productType = this.productType, registerTime = LocalDateTime.now())