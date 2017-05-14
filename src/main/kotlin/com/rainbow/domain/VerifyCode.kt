/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import jodd.datetime.JDateTime
import jodd.util.RandomString
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Document(collection = "verify_code")
class VerifyCode() : IDEntity() {
    // 手机号
    var mobile: String? = null

    // 验证码
    var code: String? = null

    // 状态: -1 过期, 0 未使用, 1 已验证, 2 已使用(注册, 修改密码等)
    var status: Int? = null

    // 过期时间
    var expire: Date? = null

    // 创建时间
    var create: Date? = null

    // 客户端
    var client: String? = null

    constructor(client: String, mobile: String) : this() {
        this.client = client
        this.mobile = mobile
        this.code = RandomString.getInstance().randomNumeric(6)
        this.status = 0
        this.expire = JDateTime().addMinute(10).convertToDate()
        this.create = Date()
    }
}