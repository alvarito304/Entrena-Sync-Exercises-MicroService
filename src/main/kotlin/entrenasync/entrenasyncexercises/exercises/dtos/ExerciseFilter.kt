package entrenasync.entrenasyncexercises.exercises.dtos

import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel

/**
 * DTO para agrupar filtros opcionales en la consulta de ejercicios.
 */
data class ExerciseFilter(
    val name: String? = null,
    val description: String? = null,
    val bodyPart: BodyPart? = null,
    val muscleGroup: MuscleGroup? = null,
    val equipment: String? = null,
    val minCalories: Int? = null,
    val maxCalories: Int? = null,
    val difficulty: DifficultyLevel? = null
)
