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
@Document(collection = "log")
class LogHelper : IDEntity() {

    var method: String? = null

    var url: String? = null

    var ip: String? = null

    var date: Date? = null

    var params: Map<String, Any>? = null
}