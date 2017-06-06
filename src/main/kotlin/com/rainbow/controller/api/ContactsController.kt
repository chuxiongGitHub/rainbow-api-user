package com.rainbow.controller.api

import com.rainbow.commons.NeedUser
import com.rainbow.entity.Contacts
import com.rainbow.service.ContactService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by rainbow on 2017/6/6.
 *一事专注，便是动人；一生坚守，便是深邃！
 */
@RestController
@RequestMapping("/api/v1/contacts")
class ContactsController {


    @Autowired
    lateinit private var contactsService: ContactService

    @NeedUser
    @GetMapping
    fun list(@RequestAttribute userUUID: String) = contactsService.list(userUUID)


    @NeedUser
    @PostMapping
    fun save(@RequestAttribute userUUID: String, @RequestBody contacts: Contacts): Contacts {
        contacts.userUUID = userUUID
        contactsService.save(contacts)

        return contacts
    }

    @NeedUser
    @DeleteMapping("/{uuid}")
    fun remove(@RequestAttribute userUUID: String, @PathVariable uuid: String): ResponseEntity<Any> {
        contactsService.remove(userUUID, uuid)

        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

}