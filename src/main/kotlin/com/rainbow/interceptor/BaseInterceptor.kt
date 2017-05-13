/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.interceptor

import com.rainbow.commons.exception.UserApiException
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
class BaseInterceptor : HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any?): Boolean {

        val rainbowId = if (request.getHeader("R-DEBUG") != "true") {
            request.getHeader("RAINBOW-USER") ?: throw UserApiException("rainbowId不能为空", HttpStatus.BAD_REQUEST)
        } else {
            "rainbow123"
        }

        return true
    }
}