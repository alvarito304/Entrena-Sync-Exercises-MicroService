package entrenasync.entrenasyncexercises.exercises.services


import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoSnippet
import com.google.api.services.youtube.model.VideoStatus

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Service
class YoutubeUploadServiceImpl(
    private val credentialProvider: YouTubeCredentialProvider
) : YoutubeUploadService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Throws(IOException::class)
    override fun uploadVideo(file: MultipartFile): String {
        log.info("Subiendo vídeo a YouTube…")

        val credential = credentialProvider.getCredential()

        val youtube = YouTube.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("entrena-sync-exercise-app")
            .build()

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

        val mediaContent = InputStreamContent(
            file.contentType ?: "video/*",
            file.inputStream
        ).apply { length = file.size }

        val response = youtube.videos()
            .insert(listOf("snippet", "status"), videoObject, mediaContent)
            .execute()

        log.info("Vídeo subido con ID: ${response.id}")
        return response.id
    }
}
