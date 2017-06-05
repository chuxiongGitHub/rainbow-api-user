package com.rainbow

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


}
