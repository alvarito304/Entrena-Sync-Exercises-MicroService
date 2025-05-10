package entrenasync.entrenasyncexercises.exercises.services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoSnippet
import com.google.api.services.youtube.model.VideoStatus
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class YoutubeUploadServiceImpl(
    private val clientService: OAuth2AuthorizedClientService
) : YoutubeUploadService {
    private val jsonFactory = GsonFactory.getDefaultInstance()
    private val httpTransport by lazy { GoogleNetHttpTransport.newTrustedTransport() }
    private val log = LoggerFactory.getLogger(YoutubeUploadServiceImpl::class.java)

    /**
     * Sube un video a YouTube y devuelve el videoId.
     */
    @Throws(IOException::class)
    override fun uploadVideo(file: MultipartFile, auth: OAuth2AuthenticationToken): String {
        // 1) Cargar el cliente OAuth2 autorizado
        val client = clientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            auth.authorizedClientRegistrationId,
            auth.name
        )
        log.info("Cliente OAuth2: ${client.clientRegistration.clientId}")

        // 2) Crear credenciales Google a partir del token
        val accessToken = AccessToken(
            client.accessToken.tokenValue,
            /* expirationTime: */ null    // o client.accessToken.expiresAt?.toInstant()
        )

// 2) Construye GoogleCredentials con ese token y el scope necesario
        val googleCredentials = GoogleCredentials.create(accessToken)
            .createScoped(listOf("https://www.googleapis.com/auth/youtube.upload"))

// 3) Adapta a HttpRequestInitializer
        val requestInitializer = HttpCredentialsAdapter(googleCredentials)

// 4) Construye el cliente YouTube usando HttpCredentialsAdapter
        val youtube = YouTube.Builder(httpTransport, jsonFactory, requestInitializer)
            .setApplicationName("mi-app-kotlin")
            .build()
        log.info("Cliente YouTube creado")

        // 4) Metadata del video
        val snippet = VideoSnippet().apply {
            title = "Video educativo Kotlin"
            description = "Subido desde Spring Boot + Kotlin"
            tags = listOf("educativo","kotlin","springboot")
        }
        log.info("Metadata del video creada")
        val status = VideoStatus().apply {
            privacyStatus = "public"
        }
        val videoObject = Video().apply {
            this.snippet = snippet
            this.status = status
        }

        // 5) Contenido multimedia
        val mediaContent = InputStreamContent(
            "video/*",
            file.inputStream
        )

        try {
            val insert = youtube.videos()
                .insert("snippet,status", videoObject, mediaContent)
            val response = insert.execute()
            log.info("Video subido: ${response.id}")
            return response.id
        } catch (e: Exception)
        {
            log.error("Error subiendo video: ${e.message}")
            throw e
        }
        // 6) Llamada a videos.insert (parte snippet,status)

    }
}