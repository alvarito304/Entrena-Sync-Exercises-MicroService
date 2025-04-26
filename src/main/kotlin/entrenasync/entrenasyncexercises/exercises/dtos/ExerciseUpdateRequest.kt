package entrenasync.entrenasyncexercises.exercises.dtos

import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ExerciseUpdateRequest (
    @field:NotBlank(message = "Exercise name must not be empty")
    @field:Size(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    var name: String?,

    @field:Size(max = 500, message = "Description must not exceed 500 characters")
    var description: String?,

    @field:NotNull(message = "You must specify the body part")
    var bodyPart: BodyPart?,

    @field:NotNull(message = "You must indicate the main muscle group (CHEST, BACK, LEGS, ARMS, SHOULDERS, CORE, FULL_BODY, UPPER_BODY)")
    var muscleGroup: MuscleGroup?,

    @field:Size(max = 100, message = "Equipment must not exceed 100 characters")
    var equipment: String?,

    @field:Min(value = 0, message = "Calories burned cannot be negative")
    var caloriesBurned: Int?,

    @field:NotNull(message = "You must set a difficulty level (BEGINNER, INTERMEDIATE, ADVANCED)")
    var difficulty: DifficultyLevel?,

    var videoUrl: String?,
)
