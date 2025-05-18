package entrenasync.entrenasyncexercises

import entrenasync.entrenasyncexercises.exercises.services.YouTubeCredentialProvider
import entrenasync.entrenasyncexercises.exercises.services.YoutubeUploadServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import entrenasync.entrenasyncexercises.config.TestConfig

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestConfig::class])
@ComponentScan(basePackages = [
    "entrenasync.entrenasyncexercises.exercises.services",
    "entrenasync.entrenasyncexercises.exercises.controllers"
])
class EntrenaSyncExercisesApplicationTests {

    @Autowired(required = false)
    private var youtubeUploadService: YoutubeUploadServiceImpl? = null

    @Test
    fun contextLoads() {
        // Verificamos que el contexto se carga correctamente
    }

}
