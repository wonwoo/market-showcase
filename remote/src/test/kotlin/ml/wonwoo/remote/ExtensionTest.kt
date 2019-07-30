package ml.wonwoo.remote

import org.assertj.core.api.AbstractStringAssert
import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowableTypeAssert
import org.mockito.Mockito
import reactor.test.StepVerifier
import java.time.Duration


infix fun <T : AbstractStringAssert<out T>> AbstractStringAssert<out T>.eq(value: Any?): T? =
    this.isEqualTo(value)

fun <T> StepVerifier.Step<T>.expectCompleteAndVerify(): Duration =
    this.expectComplete()
        .verify()

inline fun <reified T : Throwable> assertThatExceptionOfType(): ThrowableTypeAssert<T> =
    Assertions.assertThatExceptionOfType(T::class.java)

inline fun <reified T> any(): T = Mockito.any()
