package entrenasync.entrenasyncexercises.exercises.dtos

import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import java.time.LocalDateTime

data class ExerciseResponse(

    var id: ObjectId,

    var name: String,

    var description: String? = null,

    var bodyPart: BodyPart,

    var muscleGroup: MuscleGroup,

    var equipment: String? = null,

    var caloriesBurned: Int? = null,

    var difficulty: DifficultyLevel = DifficultyLevel.BEGINNER,

    var videoUrl: String? = null,

    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    )
