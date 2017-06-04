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
@Document(collection = "app")
class App : IDEntity() {

    //应用编号
    var uuid: String? = null

    //应用描述
    var desc: String? = null
}