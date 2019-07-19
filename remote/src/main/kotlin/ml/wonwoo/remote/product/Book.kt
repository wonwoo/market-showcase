package ml.wonwoo.remote.product

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class Book(

    @Id
    val id: String,

    val name: String,

    val image: String,

    val author: String

) {
    constructor(name: String, image: String, author: String) :
        this(UUID.randomUUID().toString(), name, image, author)
}