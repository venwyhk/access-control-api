package com.github.leon.aci.web.base

import arrow.core.Option
import arrow.core.Try
import arrow.core.getOrElse
import arrow.core.toOption
import com.github.leon.aci.domain.Attachment
import com.github.leon.aci.domain.BaseEntity
import com.github.leon.aci.domain.User
import com.github.leon.aci.extenstions.copyFrom
import com.github.leon.aci.service.UserService
import com.github.leon.aci.service.base.BaseService
import com.github.leon.bean.JpaBeanUtil
import org.joor.Reflect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.io.Serializable
import java.lang.reflect.Field
import javax.persistence.EntityManager
import javax.servlet.http.HttpServletRequest

abstract class BaseController<T, ID : Serializable> {

    @Autowired
    lateinit var entityManager: EntityManager
    @Autowired
    lateinit var baseService: BaseService<T, ID>
    @Autowired
    private val userService: UserService? = null

    protected val loginUser: User
        get() = SecurityContextHolder.getContext().authentication.principal as User


    open fun page(pageable: Pageable, request: HttpServletRequest): ResponseEntity<Page<T>> {
        val page = baseService.findBySecurity(request.method, request.requestURI, request.parameterMap, pageable)
        return ResponseEntity.ok(page)
    }


    open fun findOne(@PathVariable id: ID, request: HttpServletRequest): ResponseEntity<T> {
        return ResponseEntity.ok(baseService.findOneBySecurity(id, request.method, request.requestURI))
    }


    open fun saveOne(@Validated @RequestBody input: T, request: HttpServletRequest): ResponseEntity<*> {
        syncSeleceOneFromDb(input as BaseEntity)
        return ResponseEntity.ok(baseService.saveBySecurity(input, request.method, request.requestURI))
    }


    open fun updateOne(@PathVariable id: ID, @Validated @RequestBody input: T, request: HttpServletRequest): ResponseEntity<*> {
        val persisted = baseService.findOne(id)
        val merged  = (persisted as Any).copyFrom(input)
        syncSeleceOneFromDb(merged as BaseEntity)
        baseService.saveBySecurity(merged, request.method, request.requestURI)
        return ResponseEntity.ok(merged)
    }


    open fun deleteOne(@PathVariable id: ID, request: HttpServletRequest): ResponseEntity<*> {
        baseService.deleteBySecurity(id, request.method, request.requestURI)
        return ResponseEntity.noContent().build<Any>()
    }

    protected fun syncFromDb(baseEntity: BaseEntity) {
        var fields = baseEntity.javaClass.declaredFields.toList()
        if (baseEntity.javaClass.superclass == User::class.java) {
            fields += listOf(*User::class.java.declaredFields)
        }
        fields.forEach { field ->
            val type = field.type
            println(type)
            if (BaseEntity::class.java.isAssignableFrom(field.type)) {
                val option = getObject(baseEntity, field, type)
                if (option.isDefined()) {
                    Reflect.on(baseEntity).set(field.name, option.get())
                }
            } else if (field.type.isAssignableFrom(MutableList::class.java)) {
                val list = baseEntity.toOption()
                        .flatMap { it -> Reflect.on(it).get<Any>(field.name).toOption() }
                        .map { e -> e as MutableList<out BaseEntity> }
                        .getOrElse { listOf<BaseEntity>() }
                        .map { obj ->
                            val id = Reflect.on(obj).get<Any>("id")
                            when (id) {
                                null -> obj
                                else -> entityManager.find(obj::class.java, id)
                            }
                        }
                if (!list.isEmpty()) {
                    Reflect.on(baseEntity).set(field.name, list)
                }

            }
        }
    }

    protected fun syncSeleceOneFromDb(baseEntity: BaseEntity) {
        var fields = baseEntity.javaClass.declaredFields.toList()
        if (baseEntity.javaClass.superclass == User::class.java) {
            fields += listOf(*User::class.java.declaredFields)
        }
        fields.forEach { field ->
            val type = field.type
            println(type)
            if (BaseEntity::class.java.isAssignableFrom(field.type)) {
                val option = getObject(baseEntity, field, type)
                if (option.isDefined()) {
                    Reflect.on(baseEntity).set(field.name, option.get())
                }
            }else if (field.type.isAssignableFrom(MutableList::class.java)) {
                val list = baseEntity.toOption()
                        .flatMap { it -> Reflect.on(it).get<Any>(field.name).toOption() }
                        .map { it as MutableList<out BaseEntity> }
                        .getOrElse { listOf<BaseEntity>() }
                        .filter {
                            it is Attachment
                        }
                        .map { obj ->
                            val id = Reflect.on(obj).get<Any>("id")
                            when (id) {
                                null -> obj
                                else -> entityManager.find(obj::class.java, id)
                            }
                        }
                if (!list.isEmpty()) {
                    Reflect.on(baseEntity).set(field.name, list)
                }

            }
        }
    }

    private fun getObject(baseEntity: BaseEntity, field: Field, type: Class<*>): Option<*> {
        return baseEntity.toOption()
                .flatMap { Reflect.on(baseEntity).get<Any>(field.name).toOption() }
                .flatMap { Reflect.on(it).get<Any>("id").toOption() }
                .map { entityManager!!.find(type, it) }
    }

    protected fun getUser(baseEntity: BaseEntity): User {
        val userTry = Try { Reflect.on(baseEntity).get<Any>("user") as User }
        return if (userTry.isSuccess()) {
            userTry.get().toOption()
                    .map { user -> userService!!.findOne(user.id as Long) }
                    .getOrElse { loginUser }
        } else {
            loginUser
        }

    }


}
