/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons

import com.fasterxml.jackson.databind.ObjectMapper
import com.rainbow.entity.LogHelper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@Aspect
@Component
class HttpAspect {

    private val logger by lazy { LoggerFactory.getLogger(HttpAspect::class.java) }

    private val objectMapper = ObjectMapper()

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    @Pointcut("execution(public * com.rainbow.controller.*.*(..))")
    fun log() {
    }

    @Before("log()")
    fun doBefore(jointPoint: JoinPoint) {

        logger.info("开始获取请求接口")

        val attributes: ServletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes

        val request: HttpServletRequest = attributes.request

        logger.info("请求url={}", request.requestURL)

        logger.info("请求IP={}", request.remoteAddr)

        logger.info("请求类方法class={}", "${jointPoint.signature.declaringType}.${jointPoint.signature.name}")


        logger.info("开始初始化日志信息")
        val logHelper = LogHelper()
        logHelper.date = Date()
        logHelper.ip = request.remoteAddr
        logHelper.method = request.method
        logHelper.url = request.requestURI
        logHelper.params = mapOf("params" to objectMapper.writeValueAsString(jointPoint.args))

        mongoTemplate.insert(logHelper)

        @After("log()")
        fun doAfter() {
        }
    }

    @AfterReturning(returning = "any", pointcut = "log()")
    fun afterReturning(any: Any?) {
        logger.info("response={}", objectMapper.writeValueAsString(any))
    }


}