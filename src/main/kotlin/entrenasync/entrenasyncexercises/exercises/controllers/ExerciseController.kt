package entrenasync.entrenasyncexercises.exercises.controllers

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import entrenasync.entrenasyncexercises.exercises.services.ExerciseService
import entrenasync.entrenasyncexercises.exercises.services.YoutubeUploadService
import org.bson.types.ObjectId

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/Exercises")
@CrossOrigin(origins = ["http://localhost:4200"])
class ExerciseController (
    private val exerciseService : ExerciseService,
){

    @GetMapping
    fun getExercises(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) bodyPart: BodyPart?,
        @RequestParam(required = false) muscleGroup: MuscleGroup?,
        @RequestParam(required = false) equipment: String?,
        @RequestParam(required = false) minCalories: Int?,
        @RequestParam(required = false) maxCalories: Int?,
        @RequestParam(required = false) difficulty: DifficultyLevel?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "ASC") direction: String
    ): ResponseEntity<Page<ExerciseResponse>> {
        // 1) DTO de filtros
        val filter = ExerciseFilter(
            name,
            description,
            bodyPart,
            muscleGroup,
            equipment,
            minCalories,
            maxCalories,
            difficulty
        )

        // 2) Construir Sort
        val sort = if (direction.equals("ASC", ignoreCase = true)) {
            Sort.by(sortBy).ascending()
        } else {
            Sort.by(sortBy).descending()
        }

        // 3) Construir Pageable
        val pageable = PageRequest.of(page, size, sort)

        // 4) Llamada al servicio
        val pageResult = exerciseService.getExercises(filter, pageable)

        // 5) Construir cabeceras Link
        val uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest()
        val headers = HttpHeaders()
        if (pageResult.hasNext()) {
            val nextUri = uriBuilder.replaceQueryParam("page", page + 1).build().toUri()
            // Esto sigue la especificación RFC 5988
            headers.add(HttpHeaders.LINK, "<$nextUri>; rel=\"next\"")
        }
        if (pageResult.hasPrevious()) {
            val prevUri = uriBuilder.replaceQueryParam("page", page - 1).build().toUri()
            // Esto sigue la especificación RFC 5988
            headers.add(HttpHeaders.LINK, "<$prevUri>; rel=\"prev\"")
        }

        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Link")

        return ResponseEntity(pageResult, headers, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getExerciseById(@PathVariable id: ObjectId): ResponseEntity<ExerciseResponse> {
        return ResponseEntity.ok()
            .body(exerciseService.getExerciseById(id))
    }

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createExercise(
        @RequestPart("exercise") exercise: ExerciseCreateRequest,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<ExerciseResponse> {
        val created = exerciseService.createExercise(exercise, file)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(created)
    }

    @PutMapping(
        path = ["/{id}"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateExercise(
        @PathVariable id: ObjectId,
        @RequestPart("exercise") exercise: ExerciseUpdateRequest,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<ExerciseResponse> {
        val updated = exerciseService.updateExercise(id, exercise, file)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteExercise(@PathVariable id: ObjectId): ResponseEntity<Void> {
        exerciseService.deleteExercise(id)
        return ResponseEntity.noContent().build()
    }
}