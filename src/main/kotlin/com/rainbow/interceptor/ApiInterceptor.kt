/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.interceptor

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
class ApiInterceptor:HandlerInterceptorAdapter() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return super.preHandle(request, response, handler)
    }
}