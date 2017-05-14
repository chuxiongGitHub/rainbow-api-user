/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@Document(collection = "token")
class Token() : IDEntity() {
    // 客户端 UUID
    var client: String? = null
    //用户UUID
    var user: String? = null

    //访问状态 -1 禁止访问 1 正常
    var status: Int? = null

    //首次访问时间
    var firstTime: Date? = null

    //最后访问时间
    var lastTime: Date? = null

    //是否允许重复登录 0 禁止重复登录 1 允许重复登录
    var multiLogin: Int? = null


    constructor(client: String,user: String, multiLogin: Int = 1) : this() {
        this.client=client
        this.user = user
        this.status = 1
        this.firstTime = Date()
        this.multiLogin = multiLogin
    }


}