package entrenasync.entrenasyncexercises.exercises.services

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface YoutubeUploadService {
    fun uploadVideo(file: MultipartFile, auth: OAuth2AuthenticationToken): String;
}