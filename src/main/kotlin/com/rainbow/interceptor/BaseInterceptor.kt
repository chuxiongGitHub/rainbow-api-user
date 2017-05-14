/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.rainbow.commons.exception.UserApiException
import com.rainbow.config.ApiConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
class BaseInterceptor : HandlerInterceptorAdapter() {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any?): Boolean {

        try {
            val rainbowId = if (request.getHeader("R-DEBUG") != "true") {
                request.getHeader("RAINBOW-USER") ?: throw UserApiException("rainbowId不能为空", HttpStatus.BAD_REQUEST)
            } else {
                "rainbow123"
            }

            val rainbow = mongoTemplate.findOne(Query.query(Criteria("rainbowId").`is`(rainbowId).and("status").`is`(1)), ApiConfig::class.java) ?: throw UserApiException("无效的应用授权")

            request.setAttribute("rainbow", rainbow)

            return true
        } catch (ex: UserApiException) {
            response.status = 400
            response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            val stream = response.outputStream
            stream.write(ObjectMapper().writeValueAsBytes(mapOf("code" to ex.status, "message" to (ex.message ?: "未知错误"))))
            stream.flush()
            stream.close()
            return false
        }
    }
}