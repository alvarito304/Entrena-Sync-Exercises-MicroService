package entrenasync.entrenasyncexercises.exercises.services

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface YoutubeUploadService {
    fun uploadVideo(file: MultipartFile): String;
}