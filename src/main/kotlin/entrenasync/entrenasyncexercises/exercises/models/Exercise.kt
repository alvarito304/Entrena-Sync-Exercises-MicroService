package entrenasync.entrenasyncexercises.exercises.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime
import jakarta.validation.constraints.*
import org.springframework.data.mongodb.core.index.Indexed

@Document("Exercises")
@TypeAlias("Exercises")
data class Exercise(

    @Id
    var id: ObjectId,

    @field:NotBlank(message = "Exercise name must not be empty")
    @field:Size(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    // puede dar fallos
    @Indexed(unique = true)
    var name: String,

    @field:Size(max = 500, message = "Description must not exceed 500 characters")
    var description: String? = null,

    @field:NotNull(message = "You must specify the body part")
    var bodyPart: BodyPart,

    @field:NotNull(message = "You must indicate the main muscle group")
    var muscleGroup: MuscleGroup,

    @field:Size(max = 100, message = "Equipment must not exceed 100 characters")
    var equipment: String? = null,

    @field:Min(value = 0, message = "Calories burned cannot be negative")
    var caloriesBurned: Int,

    @field:NotNull(message = "You must set a difficulty level")
    var difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,

    var videoUrl: String? = null,

    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),


) {
    @JsonProperty("id")
    fun get_id(): String {
        return id.toHexString()
    }
}

enum class BodyPart {
    NECK, SHOULDERS, ARMS, CHEST, BACK, CORE, HIPS, LEGS, CALVES
}

enum class MuscleGroup {
    CHEST, BACK, LEGS, ARMS, SHOULDERS, CORE, FULL_BODY, UPPER_BODY
}

enum class DifficultyLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}