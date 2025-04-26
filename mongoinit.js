
// init-mongo.js

// 1) Conectamos/creamos la base de datos "exercises"
db = db.getSiblingDB("exercises");

// 2) Creamos el usuario admin con rol readWrite sobre "exercises"
db.createUser({
    user: 'admin',
    pwd: 'adminPassword123',
    roles: [
        { role: 'readWrite', db: 'exercises' }
    ]
});

// 3) Seed: insertamos los 17 ejercicios solicitados
db.Exercises.insertMany([
    {
        name: "Neck Stretch",
        description: "Estiramiento suave de los músculos del cuello para mejorar la movilidad y reducir tensión.",
        bodyPart: "NECK",
        muscleGroup: "UPPER_BODY",
        equipment: null,
        caloriesBurned: 5,
        difficulty: "BEGINNER",
        videoUrl: "https://www.sparkpeople.com/resource/calories_burned.asp?exercise=56",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Dumbbell Lateral Raise",
        description: "Elevación lateral de mancuernas que aísla la cabeza medial del deltoides.",
        bodyPart: "SHOULDERS",
        muscleGroup: "SHOULDERS",
        equipment: "Dumbbells",
        caloriesBurned: 4,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://www.muscleandstrength.com/exercises/dumbbell-lateral-raise.html",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Biceps Curl",
        description: "Flexión de codo con mancuerna para trabajar el bíceps braquial.",
        bodyPart: "ARMS",
        muscleGroup: "ARMS",
        equipment: "Dumbbells",
        caloriesBurned: 4,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/how-to-do-the-biceps-arm-curl-3498604",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Bench Press",
        description: "Press de banca con barra, activa pectorales, deltoides anteriores y tríceps.",
        bodyPart: "CHEST",
        muscleGroup: "CHEST",
        equipment: "Barbell",
        caloriesBurned: 5,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://en.wikipedia.org/wiki/Bench_press",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Pull-Up",
        description: "Dominadas en barra fija: trabaja dorsal ancho, bíceps y romboides.",
        bodyPart: "BACK",
        muscleGroup: "BACK",
        equipment: "Pull-up bar",
        caloriesBurned: 10,
        difficulty: "ADVANCED",
        videoUrl: "https://www.webmd.com/fitness-exercise/how-to-do-pull-ups",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Plank",
        description: "Mantén cuerpo en línea recta apoyado en antebrazos y puntas de pies; fortalece core.",
        bodyPart: "CORE",
        muscleGroup: "CORE",
        equipment: null,
        caloriesBurned: 7,
        difficulty: "BEGINNER",
        videoUrl: "https://health.clevelandclinic.org/plank-exercise-benefits",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Hip Thrust",
        description: "Elevación de cadera con hombros apoyados en banco; focaliza glúteo mayor y cadena posterior.",
        bodyPart: "HIPS",
        muscleGroup: "LEGS",
        equipment: "Barbell",
        caloriesBurned: 8,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://www.healthline.com/health/fitness-exercise/hip-thrusts",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Squat",
        description: "Sentadilla con peso corporal o barra que trabaja cuádriceps, glúteos e isquiotibiales.",
        bodyPart: "LEGS",
        muscleGroup: "LEGS",
        equipment: null,
        caloriesBurned: 6,
        difficulty: "BEGINNER",
        videoUrl: "https://www.mayoclinic.org/healthy-lifestyle/fitness/multimedia/squat/vid-20084663",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Standing Calf Raise",
        description: "Elevación de talones de pie para activar gastrocnemio y sóleo.",
        bodyPart: "CALVES",
        muscleGroup: "LEGS",
        equipment: null,
        caloriesBurned: 5,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/how-to-do-calf-raises-4801090",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Deadlift",
        description: "Levantamiento de peso desde el suelo hasta la cadera; trabaja cadena posterior.",
        bodyPart: "LEGS",
        muscleGroup: "BACK",
        equipment: "Barbell",
        caloriesBurned: 8,
        difficulty: "ADVANCED",
        videoUrl: "https://www.physio-pedia.com/Deadlift_Exercise",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Triceps Dips",
        description: "Fondos en paralelas o silla para focalizar la porción larga del tríceps.",
        bodyPart: "ARMS",
        muscleGroup: "ARMS",
        equipment: "Chair",
        caloriesBurned: 5,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/the-chair-dip-triceps-exercise-3120734",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Burpee",
        description: "Secuencia de sentadilla, plancha y salto que involucra todo el cuerpo.",
        bodyPart: "CORE",
        muscleGroup: "FULL_BODY",
        equipment: null,
        caloriesBurned: 10,
        difficulty: "ADVANCED",
        videoUrl: "https://en.wikipedia.org/wiki/Burpee_(exercise)",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    {
        name: "Push-Up",
        description: "Flexión de brazos en suelo; trabaja pectorales, tríceps y deltoides anteriores.",
        bodyPart: "CHEST",
        muscleGroup: "UPPER_BODY",
        equipment: null,
        caloriesBurned: 8,
        difficulty: "BEGINNER",
        videoUrl: "https://en.wikipedia.org/wiki/Push-up",
        createdAt: new Date(),
        updatedAt: new Date()
    }
]);
