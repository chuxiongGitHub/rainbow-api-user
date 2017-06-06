package com.rainbow.controller.api

import com.rainbow.commons.NeedUser
import com.rainbow.entity.App
import com.rainbow.entity.Platform
import com.rainbow.entity.User
import com.rainbow.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by rainbow on 2017/6/5.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
@RestController
@RequestMapping("/api/v1/user")
class UserController {

    @Autowired
    lateinit private var userService: UserService

    @PostMapping("/register")
    fun register(@RequestAttribute app: App, @RequestBody user: User) = userService.register(app, user)


    @PostMapping("/login")
    fun login(@RequestAttribute app: App, @RequestBody user: User) = userService.login(app, user)

    @PostMapping("/bind")
    fun bind(@RequestAttribute app: App, @RequestBody platform: Platform) = userService.bind(app, platform)

    @NeedUser
    @GetMapping("/info")
    fun getUserInfo(@RequestAttribute userUUID: String) = userService.getUserInfo(userUUID)

    @NeedUser
    @PostMapping("/update")
    fun update(@RequestAttribute userUUID: String, @RequestBody map: Map<String, String>): ResponseEntity<Any> {
        val entity = map.entries.first()
        userService.update(userUUID, entity.key, entity.value)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)

    }
}