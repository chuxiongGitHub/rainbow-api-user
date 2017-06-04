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
@Document(collection = "contacts")
class Contacts : IDEntity() {

    //唯一编码
    var uuid: String? = null

    //姓名
    var name: String? = null

    //电话
    var mobile: String? = null

    //证件号码
    var cardNo: String? = null

    //证件类型
    var cartType: String? = null

    //地址
    var address: String? = null

    //对应所属用户
    var userUUID: String? = null

    //护照
    var passport: String? = null

    //港澳通行证
    var hongKong: String? = null

}