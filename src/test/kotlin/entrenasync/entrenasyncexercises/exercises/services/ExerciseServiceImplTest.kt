package entrenasync.entrenasyncexercises.exercises.services

import entrenasync.entrenasyncexercises.exercises.dtos.*
import entrenasync.entrenasyncexercises.exercises.exceptions.ExerciseNotDeleted
import entrenasync.entrenasyncexercises.exercises.exceptions.ExerciseNotFound
import entrenasync.entrenasyncexercises.exercises.mapper.toEntity
import entrenasync.entrenasyncexercises.exercises.mapper.toResponse
import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import entrenasync.entrenasyncexercises.exercises.repositories.ExerciseRepository
import io.mockk.*
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.web.multipart.MultipartFile
import java.util.Optional

@ExtendWith(MockKExtension::class)
class ExerciseServiceImplTest {

    @MockK
    lateinit var exerciseRepository: ExerciseRepository

    @MockK
    lateinit var youtubeUploadService: YoutubeUploadServiceImpl

    @InjectMockKs
    lateinit var service: ExerciseServiceImpl

    // Sample IDs and DTOs
    private val sampleId = ObjectId.get()
    private val sampleCreateRequest = ExerciseCreateRequest(
        name = "Push-up",
        description = "Basic push-ups",
        bodyPart = BodyPart.CHEST,
        muscleGroup = MuscleGroup.UPPER_BODY,
        equipment = "None",
        caloriesBurned = 50,
        difficulty = DifficultyLevel.BEGINNER,
        videoUrl = null
    )
    private val sampleEntity by lazy {
        sampleCreateRequest.toEntity().apply {
            id = sampleId
            videoUrl = "oldUrl"
        }
    }
    private val sampleResponse by lazy { sampleEntity.toResponse() }

    @Test
    fun `getExercises returns paged ExerciseResponses`() {
        val filter = ExerciseFilter(name = "Push-up", description = null, bodyPart = null,
            muscleGroup = null, equipment = null, minCalories = null, maxCalories = null,
            difficulty = null)
        val pageable = PageRequest.of(0, 5)
        val page = PageImpl(listOf(sampleEntity), pageable, 1)
        every { exerciseRepository.findWithFilters(filter, pageable) } returns page

        val result = service.getExercises(filter, pageable)

        assertEquals(1, result.totalElements)
        assertEquals(sampleResponse, result.content[0])
        verify { exerciseRepository.findWithFilters(filter, pageable) }
    }

    @Test
    fun `getAllExercises returns all`() {
        every { exerciseRepository.findAll() } returns listOf(sampleEntity)

        val result = service.getAllExercises()

        assertEquals(1, result.size)
        assertEquals(sampleResponse, result[0])
        verify { exerciseRepository.findAll() }
    }

    @Test
    fun `getExerciseById returns when found`() {
        every { exerciseRepository.findById(sampleId) } returns Optional.of(sampleEntity)

        val resp = service.getExerciseById(sampleId)

        assertEquals(sampleResponse, resp)
        verify { exerciseRepository.findById(sampleId) }
    }

    @Test
    fun `getExerciseById throws when not found`() {
        every { exerciseRepository.findById(sampleId) } returns Optional.empty()

        assertThrows(ExerciseNotFound::class.java) { service.getExerciseById(sampleId) }
        verify { exerciseRepository.findById(sampleId) }
    }

    @Test
    fun `createExercise without file saves and returns`() {
        val req = sampleCreateRequest
        val savedEntity = req.toEntity().apply { id = sampleId; videoUrl = null }
        every { exerciseRepository.save(any()) } returns savedEntity

        val resp = service.createExercise(req, null)

        assertEquals(sampleId.toHexString(), resp.id.toHexString())
        assertNull(resp.videoUrl)
        // Verificar que el save fue llamado con algún entity que tenga mismo nombre y sin video
        verify { exerciseRepository.save(match { it.name == req.name && it.videoUrl == null }) }
        verify(exactly = 0) { youtubeUploadService.uploadVideo(any()) }
    }

    @Test
    fun `createExercise with file uploads and saves`() {
        val req = sampleCreateRequest.copy(videoUrl = null)
        val file = mockk<MultipartFile>()
        every { youtubeUploadService.uploadVideo(file) } returns "newUrl"
        val savedEntity = req.toEntity().apply { id = sampleId; videoUrl = "newUrl" }
        every { exerciseRepository.save(any()) } returns savedEntity

        val resp = service.createExercise(req, file)

        assertEquals("newUrl", resp.videoUrl)
        verify { youtubeUploadService.uploadVideo(file); exerciseRepository.save(any()) }
    }

    @Test
    fun `updateExercise without file retains videoUrl`() {
        val updateReq = ExerciseUpdateRequest(
            name = "Push-up",
            description = "Basic push",
            bodyPart = BodyPart.CHEST,
            muscleGroup = MuscleGroup.UPPER_BODY,
            equipment = "None",
            caloriesBurned = 50,
            difficulty = DifficultyLevel.BEGINNER,
            videoUrl = null
        )
        every { exerciseRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        val updatedEntity = updateReq.toEntity(sampleEntity).apply { videoUrl = "oldUrl" }
        every { exerciseRepository.save(any()) } returns updatedEntity

        val resp = service.updateExercise(sampleId, updateReq, null)

        assertEquals("oldUrl", resp.videoUrl)
        verify { exerciseRepository.findById(sampleId); exerciseRepository.save(any()) }
        verify(exactly = 0) { youtubeUploadService.uploadVideo(any()) }
    }

    @Test
    fun `updateExercise with file updates videoUrl`() {
        val updateReq = ExerciseUpdateRequest(
            name = "Push-up",
            description = "Basic push",
            bodyPart = BodyPart.CHEST,
            muscleGroup = MuscleGroup.UPPER_BODY,
            equipment = "None",
            caloriesBurned = 50,
            difficulty = DifficultyLevel.INTERMEDIATE,
            videoUrl = null
        )
        val file = mockk<MultipartFile>()
        every { exerciseRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        every { youtubeUploadService.uploadVideo(file) } returns "updatedUrl"
        val updatedEntity = updateReq.toEntity(sampleEntity).apply { videoUrl = "updatedUrl" }
        every { exerciseRepository.save(any()) } returns updatedEntity

        val resp = service.updateExercise(sampleId, updateReq, file)

        assertEquals("updatedUrl", resp.videoUrl)
        verify { youtubeUploadService.uploadVideo(file); exerciseRepository.save(any()) }
    }

    @Test
    fun `deleteExercise deletes when exists`() {
        every { exerciseRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        every { exerciseRepository.delete(sampleEntity) } just Runs

        service.deleteExercise(sampleId)

        verifySequence { exerciseRepository.findById(sampleId); exerciseRepository.delete(sampleEntity) }
    }

    @Test
    fun `deleteExercise throws not found`() {
        every { exerciseRepository.findById(sampleId) } returns Optional.empty()

        assertThrows(ExerciseNotFound::class.java) { service.deleteExercise(sampleId) }
        verify { exerciseRepository.findById(sampleId) }
    }

    @Test
    fun `deleteExercise throws ExerciseNotDeleted on delete error`() {
        every { exerciseRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        every { exerciseRepository.delete(sampleEntity) } throws RuntimeException("fail")

        assertThrows(ExerciseNotDeleted::class.java) { service.deleteExercise(sampleId) }
        verify { exerciseRepository.delete(sampleEntity) }
    }
}
