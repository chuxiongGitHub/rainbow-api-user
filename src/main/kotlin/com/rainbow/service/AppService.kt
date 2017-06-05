/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.service

import com.rainbow.commons.exception.UserApiException
import com.rainbow.entity.App
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
@Service
class AppService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    fun getApp(uuid: String) = mongoTemplate.findOne(Query.query(Criteria("uuid").`is`(uuid)), App::class.java) ?: throw UserApiException("401", "权限错误")
}