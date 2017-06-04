/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.service

import com.rainbow.commons.exception.UserApiException
import com.rainbow.entity.App
import com.rainbow.entity.Token
import com.rainbow.entity.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.*

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
@Service
class UserService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    private val logger by lazy { LoggerFactory.getLogger(UserService::class.java) }

    //注册
    fun register(app: App, user: User): Any {
        //判断手机号码是否被注册
        //不同类型的用户用户名可以重复，相同类型的用户，用户名不能重复

        val map = mutableMapOf<String, Any?>()

        if (mongoTemplate.exists(Query.query(Criteria("mobile").`is`(user.mobile).and("role").`is`(0)), User::class.java)) {
            map.put("success", false)
            map.put("message", "手机号码已被注册")
            return map
        }
        user.uuid = UUID.randomUUID().toString()
        //默认为普通用户
        user.role = 0
        user.appUUID = app.uuid
        mongoTemplate.insert(user)

        map.put("success", true)
        map.put("data", "")

        return map
    }




    //创建token

}