package com.github.leon.sms.service

import arrow.core.None
import arrow.core.Some
import arrow.syntax.collections.firstOption
import com.github.leon.aci.enums.TaskStatus
import com.github.leon.aci.service.base.BaseService
import com.github.leon.email.dao.EmailLogDao
import com.github.leon.email.domain.EmailLog
import com.github.leon.setting.dao.SettingDao
import com.github.leon.sms.dao.MessageLogDao
import com.github.leon.sms.domain.MessageLog
import com.github.leon.sysconfig.dao.SysConfigDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MessageLogService(
        @Autowired
        var messageProviders: List<MessageProvider>,
        @Autowired
        val messageLogDao: MessageLogDao,
        @Autowired
        val emailLogDao: EmailLogDao,
        @Autowired
        val settingDao: SettingDao

) : BaseService<MessageLog, Long>() {

    fun send(message: MessageLog): Pair<String?, String> {
        val enableProvider = settingDao.findByActive(true)!!.smsProviderType!!.name

        val providerOpt = messageProviders.firstOption {
            it.javaClass.simpleName.substringBefore(MessageProvider::class.java.simpleName).equals(enableProvider, ignoreCase = true)
        }
        return when (providerOpt) {
            is Some -> providerOpt.t.send(message)
            None -> throw IllegalStateException("sms provider missing")
        }
    }

    @Transactional
    fun sendMessage() {
        val notSentMessages = messageLogDao.findByStatus(TaskStatus.TODO)
        for (smsMessage in notSentMessages) {

            smsMessage.sendTo = dealMobileNum(smsMessage.sendTo)

            val (error, response) = send(smsMessage)
            smsMessage.resp = response
            if (error == null) {
                smsMessage.status = TaskStatus.SUCCESS
            } else {
                smsMessage.status = TaskStatus.FAILURE
                val emailLog = EmailLog(
                        subject = "Sms no credit",
                        status = TaskStatus.TODO,
                        sendTo = "lei.zhou@cfgglobal.co.nz",
                        content = "Sms no credit".toByteArray()
                )
                emailLogDao.save(emailLog)
            }
            messageLogDao.save(smsMessage)
        }
    }


    private fun dealMobileNum(number: String): String {
        var number = number

        if (number.startsWith("0")) {
            number = number.substring(1).trim { it <= ' ' }
        }
        if (number.startsWith("00")) {
            number = number.substring(2).trim { it <= ' ' }
        }
        if (number.startsWith("2")) {
            number = "64" + number
        }
        if (number.startsWith("4")) {
            number = "61" + number
        }
        if (number.startsWith("1")) {
            number = "86" + number
        }

        return number
    }


}
