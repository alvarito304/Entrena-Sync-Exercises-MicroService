package entrenasync.entrenasyncexercises.exercises.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "Credentials")
data class Credential(
    @Id
    val id: String,
    val clientId: String?,
    val clientSecret: String?,
    val refreshToken: String,
    val updatedAt: Date
)