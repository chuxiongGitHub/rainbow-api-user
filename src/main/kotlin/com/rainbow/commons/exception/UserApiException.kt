/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons.exception

import org.springframework.http.HttpStatus

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
open class UserApiException(val code: String, message: String) : Exception(message)
