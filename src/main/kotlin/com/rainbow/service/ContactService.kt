package com.rainbow.service

import com.rainbow.commons.exception.UserApiException
import com.rainbow.entity.Contacts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by rainbow on 2017/6/6.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
@Service
class ContactService {

    @Autowired
    lateinit private var mongoTemplate: MongoTemplate


    fun list(userUUid: String) = mongoTemplate.find(Query.query(Criteria("userUUID").`is`(userUUid)), Contacts::class.java)


    //保存
    fun save(contacts: Contacts) {
        contacts.cardNo = contacts.cardNo!!.toLowerCase()

        if (contacts.uuid == null) {
            if (mongoTemplate.exists(Query.query(Criteria("userUUID").`is`(contacts.userUUID).and("cardType").`is`(contacts.cartType).and("cardNo").`is`(contacts.cardNo)), Contacts::class.java)) throw UserApiException("0", "联系人已经存在")

            contacts.uuid = UUID.randomUUID().toString()
            mongoTemplate.insert(contacts)
        } else {
            if (mongoTemplate.exists(Query.query(Criteria("userUUID").`is`(contacts.userUUID).
                    and("uuid").ne(contacts.uuid).
                    and("cardType").`is`(contacts.cartType).
                    and("cardNo").`is`(contacts.cardNo)), Contacts::class.java)) throw UserApiException("0", "联系人已存在")
        }
        val _contacts = mongoTemplate.findOne(Query.query(Criteria("uuid").`is`(contacts.uuid)), Contacts::class.java)
        contacts.id = _contacts.id
        mongoTemplate.save(contacts)
    }

    //删除联系人
    fun remove(userUUid: String, uuid: String) = mongoTemplate.remove(Query.query(Criteria("userUUID").`is`(userUUid).and("uuid").`is`(uuid)), Contacts::class.java)

}