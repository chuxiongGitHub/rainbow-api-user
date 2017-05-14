package com.rainbow

import com.rainbow.config.ApiConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class RainbowApiUserApplicationTests {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    @Test
    fun contextLoads() {
    }

    @Test
    fun createApiConfig() {
        val apiConfig = ApiConfig()

        apiConfig.rainbowId = "rainbow123"
        apiConfig.status = 1
        apiConfig.userId = "rainbow123"

        mongoTemplate.insert(apiConfig)
    }

}
