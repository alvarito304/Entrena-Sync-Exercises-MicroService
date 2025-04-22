package entrenasync.entrenasyncexercises.exercises.services

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import entrenasync.entrenasyncexercises.exercises.repositories.IExerciseRepository
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ExerciseServiceImpl (
    private val exerciseRepository : IExerciseRepository
) : IExerciseService  {

    override fun getExercises(pageable: Pageable): Page<ExerciseResponse> {
        TODO("Not yet implemented")
    }

    override fun getExerciseById(id: ObjectId): ExerciseResponse {
        TODO("Not yet implemented")
    }

    override fun createExercise(exercise: ExerciseCreateRequest): ExerciseResponse {
        TODO("Not yet implemented")
    }

    override fun updateExercise(id: ObjectId, exercise: ExerciseUpdateRequest): ExerciseResponse {
        TODO("Not yet implemented")
    }

    override fun deleteExercise(id: ObjectId) {
        TODO("Not yet implemented")
    }
}