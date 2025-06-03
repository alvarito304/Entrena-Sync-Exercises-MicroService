package entrenasync.entrenasyncexercises.exercises.services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField
import org.junit.jupiter.api.Assertions.*

class YouTubeCredentialProviderTest {

    private lateinit var provider: YouTubeCredentialProvider

    @BeforeEach
    fun setUp() {
        provider = YouTubeCredentialProvider()

        // Establece valores privados usando reflexión
        setPrivateProperty("refreshToken", "mock-refresh-token")
        setPrivateProperty("clientId", "mock-client-id")
        setPrivateProperty("clientSecret", "mock-client-secret")
    }

    private fun setPrivateProperty(name: String, value: String) {
        val kClass = provider::class
        val property = kClass.declaredMemberProperties.first { it.name == name }
        property.isAccessible = true
        val field = property.javaField!!
        field.isAccessible = true
        field.set(provider, value)
    }

    @Test
    fun `getCredential returns credential with correct refresh token`() {
        val credential: GoogleCredential = provider.getCredential()

        // Solo se puede comprobar el refreshToken públicamente
        assertEquals("mock-refresh-token", credential.refreshToken)
    }
}
