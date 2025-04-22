package entrenasync.entrenasyncexercises.exercises.controllers

import entrenasync.entrenasyncexercises.exercises.services.IExerciseService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Exercises")
class ExerciseController (
    private val exerciseService : IExerciseService
){
}