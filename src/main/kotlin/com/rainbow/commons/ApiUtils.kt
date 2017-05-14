/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons

import com.fasterxml.jackson.databind.ObjectMapper
import jodd.bean.BeanCopy
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@Component
class ApiUtils {

    private val REGEX_MOBILE = "^1\\d{10}$".toRegex()

    fun isMobile(mobile: String) = REGEX_MOBILE.matches(mobile)

    fun md5(str: String, salt: String = ""): String {
        val s1 = DigestUtils.md5DigestAsHex(str.toByteArray())!!
        if (salt == "") {
            return s1
        }
        return DigestUtils.md5DigestAsHex((s1 + salt).toByteArray())
    }

    fun <T> beanCopy(source: Any, target: Class<T>): T {
        val bean = target.newInstance()
        BeanCopy.fromBean(source).toBean(bean).copy()
        return bean
    }

    val mapper by lazy { ObjectMapper() }
}