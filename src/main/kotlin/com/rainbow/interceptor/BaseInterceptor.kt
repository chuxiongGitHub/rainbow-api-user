/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.rainbow.commons.ApiUtils
import com.rainbow.commons.exception.UserApiException
import com.rainbow.config.ApiConfig
import com.rainbow.domain.ClientAccount
import com.rainbow.domain.UserAccount
import com.rainbow.service.AccountService
import com.rainbow.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
class BaseInterceptor : HandlerInterceptorAdapter() {

    @Autowired
    lateinit private var accountService: AccountService

    @Autowired
    lateinit private var authService: AuthService

    @Autowired
    lateinit private var utils: ApiUtils

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            if (handler.methodParameters.find { it.parameterType == ClientAccount::class.java } != null) {
                val username = request.getHeader("X-RAINBOW-CLIENT") ?: throw UserApiException("请求头缺少X-RAINBOW-CLIENT")
                val client = accountService.getClient(username) ?: throw UserApiException("无效的X-RAINBOW-CLIENT")
                request.setAttribute("client", utils.beanCopy(client, ClientAccount::class.java))
            }
            if (handler.methodParameters.find { it.parameterType == UserAccount::class.java } != null) {
                val session = request.getHeader("X-RAINBOW-SESSIO") ?: throw UserApiException("缺少请求头X-RAINBOW-SESSION")
                val user = authService.getUserFromSession(session) ?: throw UserApiException("无效的X-RAINBOW-SESSION")
                request.setAttribute("user", utils.beanCopy(user, UserAccount::class.java))
            }
        }
        return true
    }
}