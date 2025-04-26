package entrenasync.entrenasyncexercises.exercises.mapper

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import entrenasync.entrenasyncexercises.exercises.models.Exercise
import org.bson.types.ObjectId
import java.time.LocalDateTime

fun ExerciseCreateRequest.toEntity(): Exercise{
    return Exercise(
        id = ObjectId(),
        name = this.name,
        description = this.description,
        bodyPart = this.bodyPart,
        muscleGroup = this.muscleGroup,
        equipment = this.equipment,
        caloriesBurned = this.caloriesBurned,
        difficulty = this.difficulty,
        videoUrl = this.videoUrl,

    )
}

fun ExerciseUpdateRequest.toEntity(oldExercise: Exercise): Exercise{
    return Exercise(
        id = oldExercise.id,
        name = this.name ?: oldExercise.name,
        description = this.description ?: oldExercise.description,
        bodyPart =  this.bodyPart ?: oldExercise.bodyPart,
        muscleGroup = this.muscleGroup ?: oldExercise.muscleGroup,
        equipment = this.equipment ?: oldExercise.equipment,
        caloriesBurned = this.caloriesBurned ?: oldExercise.caloriesBurned,
        difficulty = this.difficulty ?: oldExercise.difficulty,
        videoUrl = this.videoUrl,
        createdAt = oldExercise.createdAt,
        updatedAt = LocalDateTime.now()

        )
}

fun Exercise.toResponse(): ExerciseResponse{
    return ExerciseResponse(
        id = this.id,
        name = this.name,
        description = this.description,
        bodyPart = this.bodyPart,
        muscleGroup = this.muscleGroup,
        equipment = this.equipment,
        caloriesBurned = this.caloriesBurned,
        difficulty = this.difficulty,
        videoUrl = this.videoUrl,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}