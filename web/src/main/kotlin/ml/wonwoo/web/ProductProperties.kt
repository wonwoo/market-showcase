package ml.wonwoo.web

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("product")
data class ProductProperties(

    val host: String,

    val book: String,

    val movie: String

)