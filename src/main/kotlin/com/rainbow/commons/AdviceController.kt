/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.commons


import com.mongodb.MongoSocketException
import com.rainbow.commons.exception.ErrorEntity
import com.rainbow.commons.exception.UserApiException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@RestControllerAdvice
class AdviceController {
    @ExceptionHandler(Exception::class)
    fun errorHandler(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorEntity> {
        if (ex.message == null) {
            ex.printStackTrace()
        }
        val message = ex.message ?: "未知错误"

        fun build(message: String, status: HttpStatus) = ResponseEntity(ErrorEntity(message, status, request), status)

        return when (ex) {
            is UserApiException -> build(message, ex.status)
            is IllegalArgumentException -> build(message, HttpStatus.BAD_REQUEST)
            is DuplicateKeyException -> build("指定的编码记录已经存在，添加失败", HttpStatus.BAD_REQUEST)
            is MongoSocketException -> build("数据库连接超时", HttpStatus.SERVICE_UNAVAILABLE)
            else -> build(message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }
}