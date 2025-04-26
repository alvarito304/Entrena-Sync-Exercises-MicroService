package entrenasync.entrenasyncexercises

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class EntrenaSyncExercisesApplication

fun main(args: Array<String>) {
    runApplication<EntrenaSyncExercisesApplication>(*args)
}
