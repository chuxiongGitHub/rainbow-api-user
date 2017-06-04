/**
 *作者 陈彩红 创建时间： 2017/6/4.
 */
package com.rainbow.entity

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * 创建者：陈彩红 on 2017/6/4
 *每日进步一小点.
 */
@Document(collection = "token")
class Token : IDEntity() {

    //token值
    var token: String? = null

    //有效期截止日期
    var expire: Date? = null

    //关联用户
    var userUUID: String? = null

    //关联应用
    var appUUID: String? = null

    //状态
    var status: Int? = null
}