package entrenasync.entrenasyncexercises.exercises.repositories

import entrenasync.entrenasyncexercises.exercises.models.Exercise
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ExerciseRepository : MongoRepository<Exercise, ObjectId>, ExerciseRepositoryCustom