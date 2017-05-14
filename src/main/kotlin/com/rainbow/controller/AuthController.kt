/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.controller

import com.rainbow.domain.Account
import com.rainbow.domain.ClientAccount
import com.rainbow.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@RestController
@RequestMapping("/api/v1/user")
class AuthController {

    @Autowired
    lateinit private var authService: AuthService

    @PostMapping("/register")
    fun register(@RequestAttribute client: ClientAccount, @RequestBody params: Map<String, String>) = authService.register(client, params)


    @PostMapping("/login")
    fun login(
            @RequestAttribute client: ClientAccount,
            @RequestBody params: Map<String, String>,
            @RequestParam(required = false, defaultValue = "map") mode: String
    ) = authService.login(mode, client, params)


    @DeleteMapping("/logout")
    fun logout(@RequestHeader("X-RAINBOW-SESSION", required = false, defaultValue = "") session: String) = authService.logout(session)
}