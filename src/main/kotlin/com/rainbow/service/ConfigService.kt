/**
 *作者 陈彩红 创建时间： 2017/5/14.
 */
package com.rainbow.service

import com.rainbow.domain.Config
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
class ConfigService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate

    fun get(key: String, target: String = "default", defaultValue: Any? = null): Any? {
        //查找配置
        val config = get(resolveKey(key, target))

        // 如果配置存在就返回对应的值
        if (config != null) {
            return config.value ?: defaultValue
        }
        // 如果配置不存在，并且不匹配通用配置，就返回默认值
        if (!key.contains(".%s.")) {
            return defaultValue
        }
        // 如果配置不存在，且匹配通用配置，并且查询的就是通用配置，就返回默认值
        if (target == "default") {
            return defaultValue
        }
        // 返回通用配置
        return get(resolveKey(key, "default"))?.value ?: defaultValue
    }

    private fun get(key: String) = mongoTemplate.findOne(Query.query(Criteria("key").`is`(key)), Config::class.java)

    private fun resolveKey(key: String, target: String) = if (key.contains(".%s.")) key.replace(".%s.", ".$target.") else key
}