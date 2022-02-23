package com.github.leon.aci.web.api

import com.github.leon.aci.dao.UserDao
import com.github.leon.aci.domain.User
import com.github.leon.aci.exceptions.ApiResp
import com.github.leon.aci.service.UserService
import com.github.leon.aci.web.base.BaseController
import com.github.leon.cache.CacheClient
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BaseUserController(


) : BaseController<User, Long>() {
    val log = LoggerFactory.getLogger(BaseUserController::class.java)!!

    @Value("\${spring.application.name}")
    lateinit var application: String
    @Autowired
    lateinit var userDao: UserDao
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var cacheClient: CacheClient

    @GetMapping("profile")
    fun index(): ResponseEntity<User> {
        return ResponseEntity.ok(userService.findOne(loginUser.id!!))
    }

    @PostMapping("register")
    fun regist(@RequestBody user: User): ResponseEntity<User> {
        if (user.password != user.confirmPassword) {
            throw  IllegalArgumentException("password not equal")
        }
        user.setPassword(passwordEncoder.encode(user.password))
        userService.save(user)
        return ResponseEntity.ok(user)
    }

   /* @PostMapping("forgot-password")
    fun forgotPassword(username: String, email: String): ResponseEntity<User> {
        val user = userDao.findByUsername(username)
                ?: throw IllegalArgumentException("Username $username doesn't exist ")
        if (userService.getEmails(user).none { it == email }) {
            throw IllegalArgumentException("Email $email doesn't match ")
        }

        if (user.password != user.confirmPassword) {
            throw  IllegalArgumentException("password not equal")
        }
        val password = RandomStringUtils.randomAlphanumeric(8)
        user.setPassword(passwordEncoder.encode(password))
        userService.save(user)
        return ResponseEntity.ok(user)
    }*/


    @PostMapping("password")
    fun updatePassword(oldPassword: String, newPassword: String, confirmPassword: String): ResponseEntity<*> {
        val user = loginUser
        if (!passwordEncoder.matches(oldPassword, user.password)) {
            throw  IllegalArgumentException("old password not match")
        }
        if (newPassword != confirmPassword) {
            throw  IllegalArgumentException("new password not equal")
        } else {
            user.setPassword(passwordEncoder.encode(newPassword))
            userService.save(user)
            cacheClient.deleteByKey(application + "-" + user.username)

            val apiResp = ApiResp()
            apiResp.message = "success"
            return ResponseEntity.ok(apiResp)
        }
    }

    /* @PutMapping("/{id}/reset-password")
     fun resetPassword(@PathVariable id: Long): ResponseEntity<*> {
         val user = userService.findOne(id)
         val setting = settingDao.findByActive(true)
         val password = RandomStringUtils.randomAlphanumeric(8)
         val encyptPassword = passwordEncoder.encode(password)
         user.password = encyptPassword
         val template = "/email/pwdReset.ftl"
         val context = mapOf("user" to user,
                 "password" to password,
                 "domain" to setting.serverDomain)
         val emails = userService.getEmails(user)
         emails.forEach { email -> mailManager.sendSystem("Password Reset", email, template, context) }
         userService.save(user)
         cacheClient.deleteByKey(application + "-" + user.username)

         JsonConfig.start()
                 .include(User::class.java, Q.user.id)
                 .end()
         return ResponseEntity.responseEntityOk(user)
     }*/


}