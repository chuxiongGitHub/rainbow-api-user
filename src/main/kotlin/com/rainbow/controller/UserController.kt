/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @GetMapping
    fun test(): Any? {
        return "123"
    }
}