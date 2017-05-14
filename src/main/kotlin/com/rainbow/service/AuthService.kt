/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.service

import com.rainbow.commons.ApiUtils
import com.rainbow.commons.exception.UserApiException
import com.rainbow.domain.Account
import com.rainbow.domain.Token
import com.rainbow.domain.UserInfo
import com.rainbow.domain.VerifyCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Service
class AuthService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    @Autowired
    lateinit private var tokenService: TokenService

    @Autowired
    lateinit private var accountService: AccountService

    @Autowired
    lateinit private var utils: ApiUtils

    @Autowired
    lateinit private var redisTemplate: StringRedisTemplate

    //用户注册
    fun register(client: Account, params: Map<String, String>) {
        requireNotNull(params["mobile"], { "手机号码不能为空" })
        requireNotNull(params["password"], { "密码不能为空" })
        requireNotNull(params["code"], { "验证码不能为空" })

        verifyCode(params["mobile"]!!, params["code"]!!)

        val sub = accountService.getSub("mobile", params["mobile"]!!)
        if (sub.status != 0) throw UserApiException("该手机号已被注册")
        val user = accountService.newUser(sub, params["mobile"]!!)

        tokenService.createToken(client, user)
    }


    //登录
    fun login(mode: String, client: Account, params: Map<String, String>): Any {
        val user = when (mode) {
            "map" -> {
                requireNotNull(params["mobile"], { "手机号码不能为空" })
                requireNotNull(params["password"], { "密码不能为空" })
                val user = accountService.getUser("mobile", params["mobile"]!!) ?: throw UserApiException("无效的手机号码或密码")
                if (user.password != utils.md5(params["password"]!!, user.salt ?: "")) throw UserApiException("无效的手机号码或密码")
                user
            }
            else -> {
                throw UserApiException("无效的登录方式")
            }
        }
        return mapOf("session" to genSession(client, user))
    }

    //登出
    fun logout(session: String) = clearSession("rainbow:session:*:$session")


    //校验验证码是否正确
    private fun verifyCode(mobile: String, code: String) {
        val codeQuery = Query.query(Criteria("mobile").`is`(mobile).and("code").`is`(code).and("expire").gt(Date()))

        val verifyCode = mongoTemplate.findOne(codeQuery, VerifyCode::class.java) ?: throw UserApiException("验证码错误")

        // 只有状态为 0(未使用), 1(已验证) 的验证码有效
        if ((verifyCode.status ?: -1) !in 0..1) throw UserApiException("验证码无效")

        mongoTemplate.updateFirst(codeQuery, Update.update("status", 2), VerifyCode::class.java)
    }

    //生成session
    private fun genSession(client: Account, user: Account): String {
        val token = tokenService.refreshToken(client, user)

        val prefixKey = "rainbow:session:${user.uuid}:${client.uuid}"
        if (token.multiLogin == 0) {
            clearSession("$prefixKey:*")
        }
        val session = UUID.randomUUID().toString()

        val info = utils.mapper.writeValueAsString(wrapper(token, user))
        redisTemplate.opsForValue().set("$prefixKey:$session", info, 30, TimeUnit.MINUTES)

        return session
    }


    //清理session
    private fun clearSession(pattern: String) = redisTemplate.delete(redisTemplate.keys(pattern))

    //包装用户
    private fun wrapper(token: Token, user: Account): UserInfo {
        val sub = accountService.getMobileSub(user.uuid!!)

        val info = UserInfo()

        info.mobile = sub?.openid
        info.status = sub?.status
        info.uuid = user.uuid
        info.alias = user.alias ?: info.mobile
        info.clientStatus = token.status ?: 0
        return info
    }

    fun getUserFromSession(session: String): UserInfo? {
        val keys = redisTemplate.keys("rainbow:session:*:$session").toTypedArray()
        if (keys.size != 1) {
            return null
        }
        val info = redisTemplate.opsForValue().get(keys[0]) ?: return null
        return utils.mapper.readValue(info, UserInfo::class.java)
    }

}