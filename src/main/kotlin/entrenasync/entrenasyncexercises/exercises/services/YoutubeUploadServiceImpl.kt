package entrenasync.entrenasyncexercises.exercises.services

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoSnippet
import com.google.api.services.youtube.model.VideoStatus
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import com.google.api.client.http.HttpRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager

@Service
class YoutubeUploadServiceImpl(
    private val credentialProvider: YouTubeCredentialProvider
) : YoutubeUploadService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Throws(IOException::class)
    override fun uploadVideo(file: MultipartFile): String {
        log.info("Subiendo vídeo a YouTube…")

        // 1) Obtén credenciales con gestión de refresh automático
        val credential = credentialProvider.getCredential()

        // 2) Construye el cliente de YouTube
        val youtube = YouTube.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("entrena-sync-exercise-app")
            .build()

        // 3) Prepara metadata
        val snippet = VideoSnippet().apply {
            title = "Video ejercicio Entrena-Sync"
            description = "Subido desde la aplicación Entrena-Sync"
            tags = listOf("entrenamiento", "ejercicio", "fitness")
        }
        val status = VideoStatus().apply { privacyStatus = "public" }
        val videoObject = Video().apply {
            this.snippet = snippet
            this.status = status
        }

        // 4) Contenido multimedia
        val mediaContent = InputStreamContent(
            file.contentType ?: "video/*",
            file.inputStream
        ).apply { length = file.size }

        // 5) Llamada a la API (refresh automático si el token expiró)
        val response = youtube.videos()
            .insert(listOf("snippet", "status"), videoObject, mediaContent)
            .execute()

        log.info("Vídeo subido con ID: ${response.id}")
        return response.id
    }
}
