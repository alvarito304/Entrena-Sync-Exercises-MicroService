package entrenasync.entrenasyncexercises.exercises.exceptions

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
abstract class ExerciseException(message: String) : RuntimeException(message)