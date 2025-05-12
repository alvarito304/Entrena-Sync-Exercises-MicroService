package entrenasync.entrenasyncexercises

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
    "entrenasync.entrenasyncexercises.exercises.services",
    "entrenasync.entrenasyncexercises.exercises.controllers",
    "entrenasync.entrenasyncexercises",
])
@EnableCaching
class EntrenaSyncExercisesApplication

fun main(args: Array<String>) {
    runApplication<EntrenaSyncExercisesApplication>(*args)
}
