package entrenasync.entrenasyncexercises.exercises.repositories

import entrenasync.entrenasyncexercises.exercises.models.Exercise
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IExerciseRepository : MongoRepository<Exercise, String>