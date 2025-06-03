package entrenasync.entrenasyncexercises.exercises.services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class YouTubeCredentialProvider(
) {
    private val HTTP_TRANSPORT = NetHttpTransport()
    private val JSON_FACTORY = GsonFactory.getDefaultInstance()

    @Value("\${youtube.refresh.token}")
    private lateinit var refreshToken: String
    @Value("\${youtube.client.id}")
    private lateinit var clientId: String
    @Value("\${youtube.client.secret}")
    private lateinit var clientSecret: String

    fun getCredential(): GoogleCredential {
        val refreshToken = this.refreshToken

        val builder = GoogleCredential.Builder()
            .setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)

        if (this.clientId != null && this.clientSecret != null) {
            builder.setClientSecrets(this.clientId, this.clientSecret)
        }

        return builder.build()
            .setRefreshToken(refreshToken)
    }
}
