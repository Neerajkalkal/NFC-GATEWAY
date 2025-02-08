package com.example.nfcgateway.repository

import com.example.nfcgateway.model.employee
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.yaml.snakeyaml.events.Event.ID

@Repository
interface NfcRepository : MongoRepository<employee , String>