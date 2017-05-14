/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.config

import com.rainbow.commons.IDEntity
import org.springframework.data.mongodb.core.mapping.Document

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Document(collection = "apiConfig")
class ApiConfig :IDEntity(){
    //接口ID
    var rainbowId:String?=null

    //用户平台对应的ID
    var userId:String?=null

    //状态 0禁用  1 正常
    var status:Int?=null
}