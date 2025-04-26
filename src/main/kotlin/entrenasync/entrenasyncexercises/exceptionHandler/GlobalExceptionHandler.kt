package entrenasync.workoutsmicroservice.middleWare.exceptionHandler


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<Map<String, Any>> {
        val responseStatus = ex::class.java.getAnnotation(ResponseStatus::class.java)
        val statusCode = responseStatus?.value?.value() ?: HttpStatus.INTERNAL_SERVER_ERROR.value()

        val body = mutableMapOf<String, Any>(
            "timestamp" to LocalDateTime.now(),
            "status" to statusCode,
            "error" to (responseStatus?.value?.name ?: HttpStatus.INTERNAL_SERVER_ERROR.name),
            "message" to (ex.message ?: "Error interno"),
            "path" to request.getDescription(false)
        )
        return ResponseEntity(body, HttpStatus.valueOf(statusCode))
    }
}
