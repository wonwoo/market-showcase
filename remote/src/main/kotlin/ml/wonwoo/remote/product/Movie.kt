package ml.wonwoo.remote.product

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class Movie(

    @Id
    val id: String,

    val name: String,

    val image: String,

    val genre: String

) {
    constructor(name: String, image: String, genre: String) :
        this(UUID.randomUUID().toString(), name, image, genre)
}