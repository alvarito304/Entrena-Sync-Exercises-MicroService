package entrenasync.entrenasyncexercises.exercises.repositories

import entrenasync.entrenasyncexercises.exercises.models.Credential
import org.springframework.data.mongodb.repository.MongoRepository

interface CredentialRepository: MongoRepository<Credential,String> {
}