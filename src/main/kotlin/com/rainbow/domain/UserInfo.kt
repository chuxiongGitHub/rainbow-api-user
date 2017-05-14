/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.domain

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
class UserInfo {
    // 用户手机号
    var mobile: String? = null

    // 用户状态: 0 未注册, 1 已注册
    var status: Int? = null

    // 用户名称
    var alias: String? = null

    // 对应的客户端状态: 0 未激活, 1 已激活
    var clientStatus: Int? = null

    var uuid: String? = null
}