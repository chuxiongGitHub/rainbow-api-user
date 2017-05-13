/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import jodd.util.RandomString
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
@Document(collection = "account")
open class Account() : IDEntity() {


    @Indexed(unique = true)
    var uuid: String? = null

    //用户名
    var username: String? = null

    //密码
    var password: String? = null

    //用户别名
    var alias: String? = null

    //用户状态
    var status: Int? = null

    //用户类型
    var type: AccountType? = null

    //盐
    var salt: String? = null

    constructor(type: AccountType, username: String? = null, alias: String? = null) : this() {
        this.type = type
        this.uuid = UUID.randomUUID().toString()
        this.salt = RandomString.getInstance().randomAlphaNumeric(8)
        this.username = username
        this.alias = alias ?: username
        this.status = 1
    }

}