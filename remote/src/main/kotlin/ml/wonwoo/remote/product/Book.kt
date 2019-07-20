package ml.wonwoo.remote.product

import java.util.UUID

data class Book(

    val id: String,

    val name: String,

    val image: String,

    val author: String

) {
    constructor(name: String, image: String, author: String) :
        this(UUID.randomUUID().toString(), name, image, author)
}