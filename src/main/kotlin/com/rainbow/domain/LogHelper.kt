/**
 *作者 陈彩红 创建时间： 2017/5/13.
 */
package com.rainbow.domain

import com.rainbow.commons.IDEntity
import java.util.*

/**
 * 创建者：陈彩红 on 2017/5/13
 *每日进步一小点.
 */
class LogHelper : IDEntity() {

    //请求方法
    var method: String? = null

    //请求URL
    var url: String? = null

    //请求事件
    var date: Date? = null

    //请求ip
    var ip: String? = null

    //请求参数
    var params: Map<String, Any>? = null


}