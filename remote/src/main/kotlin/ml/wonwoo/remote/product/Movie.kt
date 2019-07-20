package ml.wonwoo.remote.product

import java.util.UUID

data class Movie(

    val id: String,

    val name: String,

    val image: String,

    val genre: String

) {
    constructor(name: String, image: String, genre: String) :
        this(UUID.randomUUID().toString(), name, image, genre)
}