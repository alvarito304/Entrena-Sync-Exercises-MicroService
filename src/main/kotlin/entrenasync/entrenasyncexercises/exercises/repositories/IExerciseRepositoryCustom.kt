package entrenasync.entrenasyncexercises.exercises.repositories

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.models.Exercise
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IExerciseRepositoryCustom {
    fun findWithFilters(filter: ExerciseFilter, pageable: Pageable): Page<Exercise>
}