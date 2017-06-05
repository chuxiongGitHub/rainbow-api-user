/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.rainbow.commons.NeedUser
import com.rainbow.commons.exception.UserApiException
import com.rainbow.service.AppService
import com.rainbow.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
class ApiInterceptor : HandlerInterceptorAdapter() {

    @Autowired
    lateinit private var appService: AppService

    @Autowired
    lateinit private var userService: UserService


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        try {
            val appUUID = request.getHeader("R-APP-ID") ?: throw UserApiException("401", "R-APP-ID不能为空")

            request.setAttribute("app", appService.getApp(appUUID))

            //判断是否需要用户验证
            if (handler is HandlerMethod && handler.hasMethodAnnotation(NeedUser::class.java)) {
                val token = request.getHeader("R-USER-TOKEN") ?: throw UserApiException("400", "R-USER-TOKEN不能为空")
                val getToken = userService.getToken(token) ?: throw UserApiException("401", "权限异常")
                request.setAttribute("userUUID", getToken.userUUID)
            }

            return true
        } catch (ex: UserApiException) {
            response.status = 400
            response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            val stream = response.outputStream
            stream.write(ObjectMapper().writeValueAsBytes(mapOf("code" to ex.code, "message" to (ex.message ?: "未知异常"))))
            stream.flush()
            stream.close()
            return false

        }


    }
}