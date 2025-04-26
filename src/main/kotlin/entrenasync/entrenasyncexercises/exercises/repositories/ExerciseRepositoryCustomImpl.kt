package entrenasync.entrenasyncexercises.exercises.repositories

import entrenasync.entrenasyncexercises.exercises.dtos.ExerciseFilter
import entrenasync.entrenasyncexercises.exercises.models.Exercise
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class ExerciseRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : IExerciseRepositoryCustom {

    override fun findWithFilters(filter: ExerciseFilter, pageable: Pageable): Page<Exercise> {
        val criteriaList = mutableListOf<Criteria>()

        filter.name?.let {
            criteriaList += Criteria.where("name").regex(it, "i")
        }
        filter.description?.let {
            criteriaList += Criteria.where("description").regex(it, "i")
        }
        filter.bodyPart?.let {
            criteriaList += Criteria.where("bodyPart").`is`(it)
        }
        filter.muscleGroup?.let {
            criteriaList += Criteria.where("muscleGroup").`is`(it)
        }
        filter.equipment?.let {
            criteriaList += Criteria.where("equipment").regex(it, "i")
        }
        if (filter.minCalories != null || filter.maxCalories != null) {
            val rangeCrit = mutableListOf<Criteria>()
            filter.minCalories?.let { rangeCrit += Criteria.where("caloriesBurned").gte(it) }
            filter.maxCalories?.let { rangeCrit += Criteria.where("caloriesBurned").lte(it) }
            criteriaList += Criteria().andOperator(*rangeCrit.toTypedArray())
        }
        filter.difficulty?.let {
            criteriaList += Criteria.where("difficulty").`is`(it)
        }

        val query = Query().apply {
            if (criteriaList.isNotEmpty()) {
                addCriteria(Criteria().andOperator(*criteriaList.toTypedArray()))
            }
            with(pageable)
        }

        val total = mongoTemplate.count(query.skip(-1).limit(-1), Exercise::class.java)
        val list = mongoTemplate.find(query, Exercise::class.java)
        return PageImpl(list, pageable, total)
    }
}
