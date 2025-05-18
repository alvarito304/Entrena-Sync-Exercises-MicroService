package entrenasync.entrenasyncexercises.config

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import entrenasync.entrenasyncexercises.exercises.services.YouTubeCredentialProvider
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("test")
class TestConfig {

    @Bean
    @Primary
    fun mockYouTubeCredentialProvider(): YouTubeCredentialProvider {
        // Creamos una instancia simulada del proveedor de credenciales
        val mockProvider = Mockito.mock(YouTubeCredentialProvider::class.java)
        
        // Configuramos el comportamiento simulado
        val mockCredential = Mockito.mock(GoogleCredential::class.java)
        Mockito.`when`(mockProvider.getCredential()).thenReturn(mockCredential)
        
        return mockProvider
    }
}
