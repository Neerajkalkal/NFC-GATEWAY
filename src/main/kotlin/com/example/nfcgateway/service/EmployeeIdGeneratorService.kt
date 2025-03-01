package com.example.nfcgateway.service



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class EmployeeIdGeneratorService(
    @Autowired
    private val mongoTemplate: MongoTemplate)
{
    companion object {
        const val ID_PREFIX = "EMP-"
        const val INITIAL_SEQ = 1000
    }

    fun generateNextEmployeeId(): String {
        val query = org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").`is`("employee_id"))
        val update = Update().inc("seq", 1)
        val options = FindAndModifyOptions().returnNew(true).upsert(true)
        val counter = mongoTemplate.findAndModify(
            query,
            update,
            options,
            Counter::class.java
        ) ?: Counter("employee_id", INITIAL_SEQ)
        return ID_PREFIX + "%04d".format(counter.seq)
    }

    @Document(collection = "counters")
    data class Counter(
        @Id val id: String,
        var seq: Int
    )
}