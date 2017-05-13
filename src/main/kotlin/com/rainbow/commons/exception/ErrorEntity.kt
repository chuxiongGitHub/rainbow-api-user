/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
class ErrorEntity() {

    var message: String? = null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var timestamp = Date()
    var status = HttpStatus.BAD_REQUEST.value()
    var error: String? = null
    var path: String? = null

    constructor(message: String, status: HttpStatus, request: HttpServletRequest) : this() {
        this.message = message
        this.status = status.value()
        this.error = status.reasonPhrase
        this.path = request.requestURI
    }
}