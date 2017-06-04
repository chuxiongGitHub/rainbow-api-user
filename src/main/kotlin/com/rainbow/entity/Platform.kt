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
@Document(collection = "platform")
class Platform : IDEntity() {

    //平台唯一授权
    var uuid: String? = null

    //平台类型
    var type: String? = null

    //关联用户
    var userUUID: String? = null

    //状态0 停用 1正常
    var status: Int? = null

}