package entrenasync.entrenasyncexercises.exercises.exceptions

import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ExerciseNotFound(id: ObjectId) :  ExerciseException("The exercise with id: $id, was not found")