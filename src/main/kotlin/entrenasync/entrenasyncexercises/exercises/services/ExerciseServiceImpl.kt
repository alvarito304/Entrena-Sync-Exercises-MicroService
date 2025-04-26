package entrenasync.entrenasyncexercises.exercises.services

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import entrenasync.entrenasyncexercises.exercises.exceptions.ExerciseNotDeleted
import entrenasync.entrenasyncexercises.exercises.exceptions.ExerciseNotFound
import entrenasync.entrenasyncexercises.exercises.mapper.toEntity
import entrenasync.entrenasyncexercises.exercises.mapper.toResponse
import entrenasync.entrenasyncexercises.exercises.repositories.ExerciseRepository
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["exercises"])
class ExerciseServiceImpl (
    private val exerciseRepository : ExerciseRepository
) : ExerciseService  {

    private val log = LoggerFactory.getLogger(ExerciseServiceImpl::class.java)

    override fun getExercises(exerciseFilter: ExerciseFilter, pageable: Pageable): Page<ExerciseResponse> {
        log.info("Getting All Exercises")
        var exercises = exerciseRepository.findWithFilters(exerciseFilter, pageable)
        return exercises.map { exercise -> exercise.toResponse() }
    }

    @CachePut(key = "#id")
    override fun getExerciseById(id: ObjectId): ExerciseResponse {
        log.info("Getting exercise with id $id")
        return exerciseRepository.findById(id).orElseThrow { ExerciseNotFound(id) }.toResponse()
    }

    @CachePut(key = "#result.id")
    override fun createExercise(exercise: ExerciseCreateRequest): ExerciseResponse {
        log.info("Creating exercise")
        val newExercise = exerciseRepository.save(exercise.toEntity())
        return newExercise.toResponse()
    }

    @CachePut(key = "#id")
    override fun updateExercise(id: ObjectId, exercise: ExerciseUpdateRequest): ExerciseResponse {
        log.info("Updating exercise with id $id")
        var oldExercise = exerciseRepository.findById(id).orElseThrow { ExerciseNotFound(id) }
        val updatedExercise = exercise.toEntity(oldExercise)
        return exerciseRepository.save(updatedExercise).toResponse()
    }

    @CacheEvict(key = "#id")
    override fun deleteExercise(id: ObjectId) {
        log.info("Deleting exercise with id $id")
        var exerciseToDelete = exerciseRepository.findById(id).orElseThrow { ExerciseNotFound(id) }
        try {
            exerciseRepository.delete(exerciseToDelete)
        } catch (e: Exception) {
            throw ExerciseNotDeleted(id, e)
        }
    }
}