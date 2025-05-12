package entrenasync.entrenasyncexercises

import entrenasync.entrenasyncexercises.exercises.services.YoutubeUploadServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.context.annotation.ComponentScan
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@ComponentScan(basePackages = [
    "entrenasync.entrenasyncexercises.exercises.services",
    "entrenasync.entrenasyncexercises.exercises.controllers"
])
class EntrenaSyncExercisesApplicationTests {

    @MockitoBean
    private lateinit var clientService: OAuth2AuthorizedClientService

    @Autowired
    private lateinit var youtubeUploadService: YoutubeUploadServiceImpl

    @Test
    fun contextLoads() {
    }

}
