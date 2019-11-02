package ml.wonwoo.web

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("product")
@ConstructorBinding
data class ProductProperties(

    val host: String,

    val book: String,

    val movie: String,

    val histories: String

)