import entrenasync.entrenasyncexercises.exercises.controllers.ExerciseController
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseCreateRequest
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseResponse
import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseUpdateRequest
import entrenasync.entrenasyncexercises.exercises.models.BodyPart
import entrenasync.entrenasyncexercises.exercises.models.DifficultyLevel
import entrenasync.entrenasyncexercises.exercises.models.MuscleGroup
import entrenasync.entrenasyncexercises.exercises.services.ExerciseService
import io.mockk.MockKAnnotations
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class ExerciseControllerTest {

    @Mock
    private lateinit var exerciseService: ExerciseService

    @InjectMocks
    private lateinit var exerciseController: ExerciseController

    private lateinit var mockRequest: MockHttpServletRequest
    private lateinit var sampleResponse: ExerciseResponse
    private lateinit var sampleCreateRequest: ExerciseCreateRequest
    private lateinit var sampleUpdateRequest: ExerciseUpdateRequest

    @BeforeEach
    fun setUp() {

        MockKAnnotations.init(this, relaxed = true)

        val id = ObjectId.get()
        sampleResponse = ExerciseResponse(
            id = id,
            name = "Push Up",
            description = "A basic push-up",
            bodyPart = BodyPart.CHEST,
            muscleGroup = MuscleGroup.CHEST,
            equipment = "None",
            caloriesBurned = 10,
            difficulty = DifficultyLevel.BEGINNER,
            videoUrl = "http://example.com/video.mp4"
        )

        sampleCreateRequest = ExerciseCreateRequest(
            name = "Push Up",
            description = "A basic push-up",
            bodyPart = BodyPart.CHEST,
            muscleGroup = MuscleGroup.CHEST,
            equipment = "None",
            caloriesBurned = 12,
            difficulty = DifficultyLevel.BEGINNER,
            videoUrl = "SI"
        )

        sampleUpdateRequest = ExerciseUpdateRequest(
            name = "Modified Push Up",
            description = "An advanced push-up",
            bodyPart = BodyPart.CHEST,
            muscleGroup = MuscleGroup.CHEST,
            equipment = "None",
            caloriesBurned = 15,
            difficulty = DifficultyLevel.BEGINNER,
            videoUrl = "http://example.com/updated_video.mp4"
        )

        mockRequest = MockHttpServletRequest().apply {
            setRequestURI("/Exercises")
            setServerName("localhost")
            setScheme("http")
            setServerPort(8080)
        }
        RequestContextHolder.setRequestAttributes(ServletRequestAttributes(mockRequest))
        ServletUriComponentsBuilder.fromCurrentRequest()
    }

    @Test
    fun `getAllExercises should return list of exercises`() {
        val list = listOf(sampleResponse)
        Mockito.`when`(exerciseService.getAllExercises()).thenReturn(list)

        val response = exerciseController.getAllExercises()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(1, response.body!!.size)
        verify(exerciseService).getAllExercises()
    }

    @Test
    fun `getExerciseById should return exercise for given id`() {
        `when`(exerciseService.getExerciseById(sampleResponse.id)).thenReturn(sampleResponse)

        val response = exerciseController.getExerciseById(sampleResponse.id)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(sampleResponse, response.body)
        verify(exerciseService).getExerciseById(sampleResponse.id)
    }

    @Test
    fun `getExercises should return paged exercises with link headers`() {
        // dado
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").ascending())
        val page: Page<ExerciseResponse> =
            PageImpl(emptyList(), pageRequest,20)

        whenever(
            exerciseService.getExercises(
                any<ExerciseFilter>(),
                any<Pageable>()
            )
        ).thenReturn(page)

        // cuando
        val response = exerciseController.getExercises(
            name       = null,
            description= null,
            bodyPart   = null,
            muscleGroup= null,
            equipment  = null,
            minCalories= null,
            maxCalories= null,
            difficulty = null,
            page       = 0,
            size       = 10,
            sortBy     = "id",
            direction  = "ASC"
        )

        // entonces
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.headers.get(HttpHeaders.LINK))
        assertEquals(page.content, response.body!!.content)

        verify(exerciseService)
            .getExercises(any<ExerciseFilter>(), any<Pageable>())
    }


    @Test
    fun `createExercise should return created exercise`() {
        val file = MockMultipartFile("file", "video.mp4", "video/mp4", "dummy".toByteArray())
        Mockito.`when`(exerciseService.createExercise(sampleCreateRequest, file)).thenReturn(sampleResponse)

        val response = exerciseController.createExercise(sampleCreateRequest, file)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(sampleResponse, response.body)
        verify(exerciseService).createExercise(sampleCreateRequest, file)
    }

    @Test
    fun `updateExercise should return updated exercise`() {
        val file = MockMultipartFile("file", "video2.mp4", "video/mp4", "updated".toByteArray())
        Mockito.`when`(exerciseService.updateExercise(sampleResponse.id, sampleUpdateRequest, file)).thenReturn(sampleResponse)

        val response = exerciseController.updateExercise(sampleResponse.id, sampleUpdateRequest, file)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(sampleResponse, response.body)
        verify(exerciseService).updateExercise(sampleResponse.id, sampleUpdateRequest, file)
    }

    @Test
    fun `deleteExercise should return no content`() {
        doNothing().`when`(exerciseService).deleteExercise(sampleResponse.id)

        val response = exerciseController.deleteExercise(sampleResponse.id)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(exerciseService).deleteExercise(sampleResponse.id)
    }
}
