package entrenasync.entrenasyncexercises.exercises.exceptions

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ExerciseNotDeleted (id: ObjectId, e: Exception): ExerciseException("Something went wrong deleting the exercise with id $id, STACKTRACE: $e")