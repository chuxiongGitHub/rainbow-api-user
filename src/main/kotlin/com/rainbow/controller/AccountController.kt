package com.rainbow.controller

import com.rainbow.commons.exception.UserApiException
import com.rainbow.domain.Account
import com.rainbow.domain.SubAccount
import com.rainbow.domain.Token
import com.rainbow.domain.UserInfo
import com.rainbow.service.AccountService
import com.rainbow.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by rainbow on 2017/5/15.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
@RestController
@RequestMapping("/api/v1/account")
class AccountController {

    @Autowired
    lateinit private var accountService: AccountService

    @Autowired
    lateinit private var tokenService: TokenService

    //激活用户对应的平台访问权限
    @PutMapping("/active")
    fun active(
            @RequestParam("client") username: String,
            @RequestParam mobile: String
    ): UserInfo {
        val sub = accountService.getSub("mobile", mobile)?:throw UserApiException("手机号未注册")
        if (sub.status != 1) {
            throw UserApiException("手机号未注册")
        }
        val user = accountService.getUser(sub.owner!!) ?: throw UserApiException("手机号未注册")
        active(username, user)

        return wrapper(sub, user)
    }

    private fun wrapper(sub: SubAccount, user: Account): UserInfo {
        val info = UserInfo()
        info.mobile = sub.openid
        info.status = sub.status
        info.uuid = user.uuid
        info.alias = user.alias ?: info.mobile
        info.clientStatus = 1
        return info
    }

    private fun active(username: String, user: Account): Token? {
        val client = accountService.getClient(username) ?: throw UserApiException("无效的客户端编码")
        return tokenService.createToken(client, user, true)
    }

}