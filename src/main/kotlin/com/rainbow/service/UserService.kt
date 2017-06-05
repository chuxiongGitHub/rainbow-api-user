/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.service

import com.rainbow.commons.exception.UserApiException
import com.rainbow.entity.App
import com.rainbow.entity.Token
import com.rainbow.entity.User
import jodd.datetime.JDateTime
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
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
        if (user.mobile.isNullOrBlank()) {
            return mapOf("success" to false, "message" to "用户名不能为空")
        }
        if (user.password.isNullOrBlank()) {
            return mapOf("success" to false, "message" to "密码不能为空")
        }
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
        map.put("data", createToken(app, user))

        return map
    }


    //登录
    fun login(app: App, user: User): Any {
        user.mobile ?: throw UserApiException("400", "用户名不能为空")
        user.password ?: throw UserApiException("400", "密码不能为空")
        val getUser = mongoTemplate.findOne(Query.query(Criteria("mobile").`is`(user.mobile)), User::class.java) ?: throw UserApiException("400", "用户名或密码错误")

        logger.info("用户${user.mobile} 登录，请求密码 ${user.password?.toLowerCase()},记录密码${user.password?.toLowerCase()}")

        if (getUser.password?.toLowerCase() == user.password?.toLowerCase() || DigestUtils.md5DigestAsHex(getUser.password?.toByteArray())?.toLowerCase() == user.password?.toLowerCase() || getUser.password?.toLowerCase() == DigestUtils.md5DigestAsHex(user.password?.toByteArray())?.toLowerCase()) {
            //登录
            return createToken(app, getUser)
        } else {
            throw UserApiException("400", "用户名或密码错误")
        }
    }


    //创建token
    fun createToken(app: App, user: User): Any {

        val token = mongoTemplate.findOne(Query.query(Criteria("status").`is`(1).and("appUUID").`is`(app.uuid).and("expire").gt(Date())), Token::class.java)
        if (token != null) {
            //延长有效期一个
            token.expire = JDateTime().addMonth(1).convertToDate()
            mongoTemplate.save(token)

            return mapOf("token" to token.token, "expire" to token.expire)
        }

        val newToken = Token()
        newToken.token = UUID.randomUUID().toString()
        newToken.expire = JDateTime().addYear(1).convertToDate()
        newToken.userUUID = user.uuid
        newToken.appUUID = app.uuid
        newToken.status = 1
        mongoTemplate.insert(newToken)

        return mapOf("token" to newToken.token, "expire" to newToken.expire)
    }

    //获取Token
    fun getToken(token: String) = mongoTemplate.findOne(Query.query(Criteria("status").`is`(1).and("token").`is`(token).and("expire").gt(Date())), Token::class.java)

}