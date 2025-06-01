# EntrenaSync Exercise Microservice

## Descripción

Este microservicio forma parte del ecosistema **EntrenaSync** y se encarga de la gestión completa de ejercicios del sistema. Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para el manejo de ejercicios, incluyendo información detallada, filtros avanzados, integración con YouTube para videos demostrativos y sistema de caché optimizado.

## Tecnologías Utilizadas

-   **Kotlin** - Lenguaje de programación principal
-   **Spring Boot 3.x** - Framework de aplicación
-   **Spring Web** - Para la creación de API REST
-   **Spring Data MongoDB** - Integración con base de datos MongoDB
-   **Spring Cache** - Sistema de caché integrado
-   **MongoDB** - Base de datos NoSQL para persistencia
-   **Jakarta Validation** - Validación de datos de entrada
-   **Gradle (Kotlin)** - Gestión de dependencias y construcción del proyecto
-   **YouTube Data API v3** - Integración para subida de videos
-   **Google OAuth2** - Autenticación con YouTube

## Características Principales

### Gestión de Ejercicios

-   ✅ Creación de nuevos ejercicios
-   ✅ Consulta de ejercicios por ID
-   ✅ Listado paginado y filtrado de ejercicios
-   ✅ Actualización de información de ejercicios
-   ✅ Eliminación de ejercicios
-   ✅ Validación completa de datos de entrada
-   ✅ Manejo global de excepciones
-   ✅ Sistema de caché para optimización de rendimiento
-   ✅ Filtros avanzados por múltiples criterios
-   ✅ Subida automática de videos a YouTube

### Modelos de Datos

-   **Exercise**: Información completa del ejercicio (nombre, descripción, grupo muscular, dificultad, calorías, video)
-   **BodyPart**: Enumeración de partes del cuerpo
-   **MuscleGroup**: Enumeración de grupos musculares
-   **DifficultyLevel**: Niveles de dificultad

## Estructura del Proyecto

```
src/main/kotlin/entrenasync/entrenasyncexercises/
├── exercises/
│   ├── controllers/
│   │   └── ExerciseController.kt           # Endpoints REST
│   ├── dtos/
│   │   ├── ExerciseCreateRequest.kt        # DTO para creación
│   │   ├── ExerciseUpdateRequest.kt        # DTO para actualización
│   │   ├── ExerciseResponse.kt             # DTO de respuesta
│   │   └── ExerciseFilter.kt               # DTO para filtros
│   ├── models/
│   │   └── Exercise.kt                     # Entidad principal
│   ├── services/
│   │   ├── ExerciseService.kt              # Interfaz del servicio
│   │   ├── ExerciseServiceImpl.kt          # Implementación del servicio
│   │   ├── YoutubeUploadService.kt         # Interfaz YouTube
│   │   ├── YoutubeUploadServiceImpl.kt     # Implementación YouTube
│   │   └── YouTubeCredentialProvider.kt    # Proveedor de credenciales
│   ├── repositories/
│   │   ├── ExerciseRepository.kt           # Repositorio principal
│   │   ├── ExerciseRepositoryCustom.kt     # Interfaz personalizada
│   │   └── ExerciseRepositoryImpl.kt       # Implementación personalizada
│   ├── mapper/
│   │   └── ExerciseMapper.kt               # Transformadores de datos
│   └── exceptions/
│       ├── ExerciseException.kt            # Excepción base
│       ├── ExerciseNotFound.kt             # Excepción específica
│       └── ExerciseNotDeleted.kt           # Excepción de eliminación
└── exceptionHandler/
    └── GlobalExceptionHandler.kt           # Manejo global de errores
```

## API Endpoints

### Exercises

| Método   | Endpoint           | Descripción                               |
| -------- | ------------------ | ----------------------------------------- |
| `GET`    | `/Exercises`       | Obtener ejercicios filtrados (paginado)  |
| `GET`    | `/Exercises/all`   | Obtener todos los ejercicios             |
| `GET`    | `/Exercises/{id}`  | Obtener ejercicio por ID                 |
| `POST`   | `/Exercises`       | Crear nuevo ejercicio (con video)       |
| `PUT`    | `/Exercises/{id}`  | Actualizar ejercicio existente           |
| `DELETE` | `/Exercises/{id}`  | Eliminar ejercicio                       |

### Filtros Disponibles

Los ejercicios pueden filtrarse por:
- **name**: Nombre del ejercicio (búsqueda parcial)
- **description**: Descripción (búsqueda parcial)
- **bodyPart**: Parte del cuerpo específica
- **muscleGroup**: Grupo muscular
- **equipment**: Equipo necesario
- **minCalories/maxCalories**: Rango de calorías quemadas
- **difficulty**: Nivel de dificultad

### Paginación y Ordenamiento

- **page**: Número de página (default: 0)
- **size**: Tamaño de página (default: 10)
- **sortBy**: Campo de ordenamiento (default: "id")
- **direction**: Dirección de ordenamiento (ASC/DESC, default: ASC)

## Configuración

### Variables de Entorno

#### YouTube Integration
-   `YOUTUBE_CLIENT_ID`: ID del cliente OAuth2 de Google
-   `YOUTUBE_CLIENT_SECRET`: Secreto del cliente OAuth2
-   `YOUTUBE_REFRESH_TOKEN`: Token de actualización para YouTube API

#### Base de Datos (Desarrollo)
-   `MONGO_HOST`: Host de MongoDB (default: exercises-microservice-mongo-db)
-   `MONGO_PORT`: Puerto de MongoDB (default: 27017)
-   `MONGO_DB`: Nombre de la base de datos (default: exercises)
-   `DATABASE_USER`: Usuario de MongoDB (default: admin)
-   `DATABASE_PASSWORD`: Contraseña de MongoDB (default: adminPassword123)

#### Base de Datos (Producción)
-   `MONGO_URI`: URI de conexión a MongoDB Atlas
-   `MONGO_DB`: Nombre de la base de datos (default: exercises)

### Profiles de Spring

-   **dev**: Configuración para desarrollo local
-   **prod**: Configuración para producción

## Soporte

Para soporte técnico o preguntas, contacta con el equipo de desarrollo de EntrenaSync.