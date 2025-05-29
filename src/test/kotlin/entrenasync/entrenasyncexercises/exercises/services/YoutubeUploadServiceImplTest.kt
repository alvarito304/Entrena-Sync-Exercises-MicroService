package entrenasync.entrenasynceercises.exercises.services

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Video
import entrenasync.entrenasyncexercises.exercises.services.YouTubeCredentialProvider
import entrenasync.entrenasyncexercises.exercises.services.YoutubeUploadServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@ExtendWith(MockKExtension::class)
class YoutubeUploadServiceImplTest {

    @MockK
    lateinit var credentialProvider: YouTubeCredentialProvider

    lateinit var service: YoutubeUploadServiceImpl

    @BeforeEach
    fun setUp() {
        service = YoutubeUploadServiceImpl(credentialProvider)
        mockkConstructor(YouTube.Builder::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `uploadVideo returns video id on success`() {
        // Mock credential
        val mockCredential = mockk<GoogleCredential>()
        every { credentialProvider.getCredential() } returns mockCredential

        // Prepare mocks for YouTube API calls
        val mockInsert = mockk<YouTube.Videos.Insert>()
        val responseVideo = Video().apply { id = "abc123" }
        every { mockInsert.execute() } returns responseVideo

        val mockVideos = mockk<YouTube.Videos>()
        every {
            mockVideos.insert(listOf("snippet", "status"), any<Video>(), any<InputStreamContent>())
        } returns mockInsert

        val mockYouTube = mockk<YouTube>()
        every { mockYouTube.videos() } returns mockVideos

        // Mock the YouTube.Builder to return our mockBuilder and mockYouTube
        val mockBuilder = mockk<YouTube.Builder>()
        every { mockBuilder.setApplicationName(any()) } returns mockBuilder
        every { mockBuilder.build() } returns mockYouTube

        every {
            anyConstructed<YouTube.Builder>().setApplicationName(any())
        } returns mockBuilder
        every {
            anyConstructed<YouTube.Builder>().build()
        } returns mockYouTube

        // Create sample file
        val content = "video data".toByteArray()
        val file: MultipartFile = MockMultipartFile("file", "test.mp4", "video/mp4", content)

        // Execute
        val resultId = service.uploadVideo(file)

        assertEquals("abc123", resultId)
        verify {
            credentialProvider.getCredential()
            mockYouTube.videos()
            mockVideos.insert(any(), any(), any())
            mockInsert.execute()
        }
    }

    @Test
    fun `uploadVideo throws IOException when inputStream fails`() {
        val file = mockk<MultipartFile>()
        every { file.contentType } returns "video/mp4"
        every { file.size } returns 10L
        every { file.inputStream } throws IOException("error")
        every { credentialProvider.getCredential() } returns mockk<GoogleCredential>()


        assertThrows(IOException::class.java) { service.uploadVideo(file) }

        verify { credentialProvider.getCredential() }
    }
}