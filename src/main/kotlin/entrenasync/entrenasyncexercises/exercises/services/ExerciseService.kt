package entrenasync.entrenasyncexercises.exercises.services

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface ExerciseService {
    fun getExercises(exerciseFilter: ExerciseFilter, pageable: Pageable): Page<ExerciseResponse>
    fun getExerciseById(id: ObjectId) : ExerciseResponse
    fun createExercise(exercise: ExerciseCreateRequest, file: MultipartFile?) : ExerciseResponse
    fun updateExercise(id: ObjectId, exercise: ExerciseUpdateRequest, file: MultipartFile?): ExerciseResponse
    fun deleteExercise(id: ObjectId)
}