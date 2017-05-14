/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.service

import com.rainbow.commons.exception.UnauthorizedException
import com.rainbow.domain.Account
import com.rainbow.domain.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.util.*

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Service
class TokenService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    @Autowired
    lateinit private var configSerivce: ConfigService

    //获取token
    fun getToken(client: Account, user: Account): Token? = mongoTemplate.findOne(buildQuery(client, user), Token::class.java)

    //刷新token
    fun refreshToken(client: Account, user: Account): Token {
        createToken(client, user)
        val update = Update.update("lastTime", Date())
        val options = FindAndModifyOptions.options().returnNew(true)
        return mongoTemplate.findAndModify(buildQuery(client, user), update, options, Token::class.java) ?: throw UnauthorizedException("无权访问")
    }


    //创建客户端对应得token
    fun createToken(client: Account, user: Account, forced: Boolean = false): Token? {
        if (!mongoTemplate.exists(buildQuery(client, user), Token::class.java)) {
            if (!forced) {
                //检查客户端配置是否允许自动开通
                val autoOpen = configSerivce.get("client.%s.autoOpen", client.username!!, false)
                if (autoOpen !is Boolean || !autoOpen) {
                    return null
                }
            }
            val multiLogin = configSerivce.get("client.%s.multiLogin", client.username!!, 1)
            val token = Token(client.uuid!!, user.uuid!!, multiLogin as Int)
            mongoTemplate.insert(token)
            return token
        }
        return null
    }

    private fun buildQuery(client: Account, user: Account) = Query.query(Criteria("client").`is`(client.uuid).and("user").`is`(user.uuid))
}