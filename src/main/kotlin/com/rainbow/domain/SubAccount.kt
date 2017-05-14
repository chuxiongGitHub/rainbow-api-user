/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.mapping.Document

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Document(collection = "sub_account")
class SubAccount() : IDEntity() {
    // 账户类型: wx 微信, mobile 手机号
    var type: String? = null

    // 第三方账号(微信号，手机号)
    var openid: String? = null

    // 所属账号
    var owner: String? = null

    // 状态: -1 停用, 0 未激活, 1 正常
    var status: Int? = null

    constructor(type: String, openid: String) : this() {
        this.type = type
        this.openid = openid
        this.status = 0
    }
}