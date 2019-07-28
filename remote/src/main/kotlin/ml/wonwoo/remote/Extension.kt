package ml.wonwoo.remote

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


inline fun <T> sleep(time: Long = 5000, unit: TimeUnit = TimeUnit.MILLISECONDS, body: () -> T): T {

    unit.sleep(time)

    return body()
}


inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)
