
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
// 3) Seed: insertamos ejercicios, al menos uno por cada BodyPart
db.Exercises.insertMany([
    // NECK
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
    // CHEST
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
    // FRONT_DELT_AND_BICEPS
    {
        name: "Biceps Curl",
        description: "Flexión de codo con mancuerna para trabajar el bíceps braquial.",
        bodyPart: "FRONT_DELT_AND_BICEPS",
        muscleGroup: "ARMS",
        equipment: "Dumbbells",
        caloriesBurned: 4,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/how-to-do-the-biceps-arm-curl-3498604",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // UPPER_CORE
    {
        name: "Plank",
        description: "Mantén cuerpo en línea recta apoyado en antebrazos y puntas de pies; fortalece core.",
        bodyPart: "UPPER_CORE",
        muscleGroup: "CORE",
        equipment: null,
        caloriesBurned: 7,
        difficulty: "BEGINNER",
        videoUrl: "https://health.clevelandclinic.org/plank-exercise-benefits",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // FOREARM
    {
        name: "Wrist Curl",
        description: "Flexión y extensión de muñeca con mancuerna para fortalecer antebrazos.",
        bodyPart: "FOREARM",
        muscleGroup: "ARMS",
        equipment: "Dumbbells",
        caloriesBurned: 3,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/wrist-curl-exercise-3120414",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // MID_CORE
    {
        name: "Russian Twist",
        description: "Giro de tronco sentado para trabajar la parte media del core.",
        bodyPart: "MID_CORE",
        muscleGroup: "CORE",
        equipment: null,
        caloriesBurned: 6,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://www.healthline.com/health/russian-twist",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // LOWER_CORE
    {
        name: "Leg Raise",
        description: "Elevación de piernas tumbado para focalizar la parte baja del core.",
        bodyPart: "LOWER_CORE",
        muscleGroup: "CORE",
        equipment: null,
        caloriesBurned: 6,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/captions/leg-raises-3120011",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // HAND
    {
        name: "Grip Squeeze",
        description: "Apretar un agarre para fortalecer la mano y los músculos de la mano.",
        bodyPart: "HAND",
        muscleGroup: "ARMS",
        equipment: "Grip Trainer",
        caloriesBurned: 2,
        difficulty: "BEGINNER",
        videoUrl: "https://www.acefitness.org/education-and-resources/lifestyle/exercise-library/179/grip-strengthener/",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // QUADRICEPS_AND_ADDUCTORS
    {
        name: "Squat",
        description: "Sentadilla con peso corporal o barra que trabaja cuádriceps, glúteos e isquiotibiales.",
        bodyPart: "QUADRICEPS_AND_ADDUCTORS",
        muscleGroup: "LEGS",
        equipment: null,
        caloriesBurned: 6,
        difficulty: "BEGINNER",
        videoUrl: "https://www.mayoclinic.org/healthy-lifestyle/fitness/multimedia/squat/vid-20084663",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // KNEE
    {
        name: "Straight Leg Raise",
        description: "Elevación de pierna estirada para movilizar y fortalecer la rodilla.",
        bodyPart: "KNEE",
        muscleGroup: "LEGS",
        equipment: null,
        caloriesBurned: 4,
        difficulty: "BEGINNER",
        videoUrl: "https://www.physio-pedia.com/Straight_Leg_Raise",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // TIBIALIS
    {
        name: "Tibialis Raise",
        description: "Flexión dorsal del pie contra resistencia para fortalecer tibial anterior.",
        bodyPart: "TIBIALIS",
        muscleGroup: "LEGS",
        equipment: "Resistance Band",
        caloriesBurned: 3,
        difficulty: "BEGINNER",
        videoUrl: "https://www.fitnesseducationonline.com/tibialis-anterior-strengthening/",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // FOOT
    {
        name: "Toe Raise",
        description: "Elevación de pies para activar músculos del pie y mejorar equilibrio.",
        bodyPart: "FOOT",
        muscleGroup: "LEGS",
        equipment: null,
        caloriesBurned: 2,
        difficulty: "BEGINNER",
        videoUrl: "https://www.healthline.com/health/foot-exercises",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // TRAPS
    {
        name: "Shoulder Shrug",
        description: "Encogimiento de hombros con pesas para trabajar trapecios.",
        bodyPart: "TRAPS",
        muscleGroup: "SHOULDERS",
        equipment: "Dumbbells",
        caloriesBurned: 4,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/dumbbell-shrug-exercise-4688340",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // UPPER_BACK
    {
        name: "Pull-Up",
        description: "Dominadas en barra fija: trabaja dorsal ancho, bíceps y romboides.",
        bodyPart: "UPPER_BACK",
        muscleGroup: "BACK",
        equipment: "Pull-up bar",
        caloriesBurned: 10,
        difficulty: "ADVANCED",
        videoUrl: "https://www.webmd.com/fitness-exercise/how-to-do-pull-ups",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // REAR_DELT_AND_TRICEPS
    {
        name: "Reverse Fly",
        description: "Apertura de brazos con mancuernas inclinado para deltoides posteriores.",
        bodyPart: "REAR_DELT_AND_TRICEPS",
        muscleGroup: "SHOULDERS",
        equipment: "Dumbbells",
        caloriesBurned: 5,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://www.verywellfit.com/dumbbell-reverse-fly-4688341",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // ELBOW
    {
        name: "Overhead Triceps Extension",
        description: "Extensión de brazos por encima de la cabeza para trabajar tríceps y codo.",
        bodyPart: "ELBOW",
        muscleGroup: "ARMS",
        equipment: "Dumbbells",
        caloriesBurned: 4,
        difficulty: "BEGINNER",
        videoUrl: "https://www.verywellfit.com/dumbbell-overhead-triceps-extension-3498313",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // LOWER_BACK
    {
        name: "Back Extension",
        description: "Extensión de tronco tumbado boca abajo para fortalecer la parte baja de la espalda.",
        bodyPart: "LOWER_BACK",
        muscleGroup: "BACK",
        equipment: null,
        caloriesBurned: 6,
        difficulty: "BEGINNER",
        videoUrl: "https://www.spine-health.com/video/lower-back-extension-exercise",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // GLUTEUS
    {
        name: "Hip Thrust",
        description: "Elevación de cadera con hombros apoyados en banco; focaliza glúteo mayor y cadena posterior.",
        bodyPart: "GLUTEUS",
        muscleGroup: "LEGS",
        equipment: "Barbell",
        caloriesBurned: 8,
        difficulty: "INTERMEDIATE",
        videoUrl: "https://www.healthline.com/health/fitness-exercise/hip-thrusts",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // HAMSTRING
    {
        name: "Deadlift",
        description: "Levantamiento de peso desde el suelo hasta la cadera; trabaja cadena posterior.",
        bodyPart: "HAMSTRING",
        muscleGroup: "BACK",
        equipment: "Barbell",
        caloriesBurned: 8,
        difficulty: "ADVANCED",
        videoUrl: "https://www.physio-pedia.com/Deadlift_Exercise",
        createdAt: new Date(),
        updatedAt: new Date()
    },
    // CALVES
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
    // BURPEE (LOWER_CORE alternative full-body)
    {
        name: "Burpee",
        description: "Secuencia de sentadilla, plancha y salto que involucra todo el cuerpo.",
        bodyPart: "LOWER_CORE",
        muscleGroup: "FULL_BODY",
        equipment: null,
        caloriesBurned: 10,
        difficulty: "ADVANCED",
        videoUrl: "https://en.wikipedia.org/wiki/Burpee_(exercise)",
        createdAt: new Date(),
        updatedAt: new Date()
    }
]);

