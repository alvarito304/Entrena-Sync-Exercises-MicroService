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

@Service
class YoutubeUploadServiceImpl(
) : YoutubeUploadService {
    private val jsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport by lazy { GoogleNetHttpTransport.newTrustedTransport() }
    private val log = LoggerFactory.getLogger(YoutubeUploadServiceImpl::class.java)

    /**
     * Sube un video a YouTube y devuelve el videoId.
     */
    @Throws(IOException::class)
    override fun uploadVideo(file: MultipartFile,accessToken: String): String {
        log.info("Starting YouTube upload process with auth: $accessToken")

        try {
            // 1) Crear credenciales desde el token manual
            val credentials = GoogleCredentials.create(AccessToken(accessToken, null))
                .createScoped(listOf("https://www.googleapis.com/auth/youtube.upload"))

            // 2) Construir cliente YouTube
            val httpRequestInitializer = HttpRequestInitializer { request: HttpRequest ->
                request.headers.authorization = "Bearer $accessToken"
            }

// Construir cliente YouTube y omitir HttpCredentialsAdapter
            val youtube = YouTube.Builder(
                NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                httpRequestInitializer
            )
                .setApplicationName("entrena-sync-exercise-app")
                .build()

            // 6) Metadata del video
            val snippet = VideoSnippet().apply {
                title = "Video ejercicio Entrena-Sync"
                description = "Subido desde la aplicación Entrena-Sync"
                tags = listOf("entrenamiento", "ejercicio", "fitness")
            }

            val status = VideoStatus().apply {
                privacyStatus = "public"
            }

            val videoObject = Video().apply {
                this.snippet = snippet
                this.status = status
            }

            // 7) Contenido multimedia
            val mediaContent = InputStreamContent(
                file.contentType ?: "video/*",
                file.inputStream
            ).apply {
                length = file.size
            }

            // 8) Llamada a videos.insert (parte snippet,status)
            val insert = youtube.videos()
                .insert(listOf("snippet", "status"), videoObject, mediaContent)
            val response = insert.execute()
            log.info("Video subido: ${response.id}")
            return response.id
        } catch (e: Exception) {
            log.error("Error subiendo video: ${e.message}", e)
            throw IOException("Error al subir video a YouTube: ${e.message}", e)
        }
    }
}