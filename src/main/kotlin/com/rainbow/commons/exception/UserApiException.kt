/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons.exception

import org.springframework.http.HttpStatus

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
open class UserApiException(message: String? = null, val status: HttpStatus=HttpStatus.BAD_REQUEST) : RuntimeException(message)
