/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Document(collection = "config")
class Config : IDEntity() {

    // 配置 key
    @Indexed(unique = true)
    var key: String? = null

    // 配置内容
    var value: Any? = null

    // 配置描述
    var desc: String? = null

    // 配置状态: 0 禁用, 1 启用
    var status: Int? = null
}