package ml.wonwoo.remote

import java.util.concurrent.TimeUnit


inline fun <T> sleep(time: Long = 5000, unit: TimeUnit = TimeUnit.MILLISECONDS, body: () -> T): T {

    unit.sleep(time)

    return body()
}

