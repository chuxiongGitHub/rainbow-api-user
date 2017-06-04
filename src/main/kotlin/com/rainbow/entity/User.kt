/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.entity

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.mapping.Document

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
@Document(collection = "user")
class User : IDEntity() {

    //全局唯一标识
    var uuid: String? = null

    //手机号码
    var mobile: String? = null

    //昵称
    var alias: String? = null

    //密码
    var password: String? = null

    //注册来源
    var appUUID: String? = null

    //用户类型: 1 管理员 0 普通用户
    var role: String? = null


}