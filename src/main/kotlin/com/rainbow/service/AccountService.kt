/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.service

import com.rainbow.commons.AccountType
import com.rainbow.commons.ApiUtils
import com.rainbow.domain.Account
import com.rainbow.domain.SubAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

/**
 * 创建者：陈彩红 on 2017/5/14
 *每日进步一小点.
 */
@Service
class AccountService {

    @Autowired
    lateinit private var utils: ApiUtils

    @Autowired
    lateinit private var mongotTemplate: MongoTemplate

    // 获取指定用户的手机号
    fun getMobileSub(owner: String): SubAccount? = mongotTemplate.findOne(Query.query(Criteria("type").`is`("mobile").and("owner").`is`(owner)), SubAccount::class.java)

    //获取或者新建子账号
    fun getSub(type: String, openId: String) = mongotTemplate.findOne(Query.query(Criteria("type").`is`(type).and("openId").`is`(openId)), SubAccount::class.java)

    //新建主账号
    fun newUser(sub: SubAccount, alias: String? = null): Account {
        val user = Account(AccountType.user)
        user.password = utils.md5(sub.openid!!.substring(5), user.salt!!)
        if (!alias.isNullOrBlank()) {
            user.alias = alias
        }
        mongotTemplate.insert(user)

        activeSub(user, sub)

        return user

    }

    //新建平台账号
    fun newClient(username: String, alias: String? = null): Account {
        val account = Account(AccountType.client, "client_" + username, alias)
        mongotTemplate.insert(account)
        return account
    }

    //新建地方账号
    fun newThird(username: String, alias: String? = null): Account {
        val account = Account(AccountType.third, "third_" + username, alias)
        mongotTemplate.insert(account)
        return account
    }

    //激活子账号
    fun activeSub(user: Account, sub: SubAccount) {
        sub.status = 1
        sub.openid = user.uuid
        mongotTemplate.save(sub)
    }

    //获取主账号
    fun getUser(uuid: String): Account? = mongotTemplate.findOne(Query.query(Criteria("uuid").`is`(uuid)), Account::class.java)

    //获取客户端
    fun getClient(username: String): Account? = mongotTemplate.findOne(Query.query(Criteria("username").`is`(username).and("type").`is`(AccountType.client)), Account::class.java)

    //新建子账号
    private fun newSub(type: String, openId: String): SubAccount {
        val sub = SubAccount(type, openId)
        mongotTemplate.insert(sub)
        return sub
    }

    //通过主账号获取子账号
    fun getUser(type: String, openId: String): Account? {
        val sub = getSub(type, openId)
        if (sub.status != 1) {
            return null
        }
        return getUser(sub.owner!!)
    }

}