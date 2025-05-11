package entrenasync.entrenasyncexercises.exercises.services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import entrenasync.entrenasyncexercises.exercises.repositories.CredentialRepository
import org.springframework.stereotype.Component

@Component
class YouTubeCredentialProvider(
    private val credentialRepository: CredentialRepository
) {
    private val HTTP_TRANSPORT = NetHttpTransport()
    private val JSON_FACTORY = GsonFactory.getDefaultInstance()

    fun getRefreshToken(): String {
        val creds = credentialRepository.findById("youtube")
            .orElseThrow { IllegalStateException("No se hallaron credenciales de YouTube en la BD") }
        return creds.refreshToken
    }

    fun getCredential(): GoogleCredential {
        val refreshToken = getRefreshToken()

        val builder = GoogleCredential.Builder()
            .setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)

        // Si quieres usar clientId/secret desde la colección, podrías hacer:
        val creds = credentialRepository.findById("youtube").get()
        if (creds.clientId != null && creds.clientSecret != null) {
            builder.setClientSecrets(creds.clientId, creds.clientSecret)
        }

        return builder.build()
            .setRefreshToken(refreshToken)
    }
}
