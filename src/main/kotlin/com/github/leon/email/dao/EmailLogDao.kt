package com.github.leon.email.dao


import com.github.leon.aci.dao.base.BaseDao
import com.github.leon.email.domain.EmailLog
import com.github.leon.email.domain.EmailServer
import org.springframework.stereotype.Repository


@Repository
interface EmailLogDao : BaseDao<EmailLog, Long> {
}

