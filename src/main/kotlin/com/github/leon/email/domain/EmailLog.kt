package com.github.leon.email.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.github.leon.aci.domain.BaseEntity
import com.github.leon.aci.enums.TaskStatus
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*


@Entity
@DynamicUpdate
@DynamicInsert

data class EmailLog(

        val subject: String? = null,
        @Lob
        @Column(length = 100000)
        @JsonIgnore
        val content: ByteArray? = null,
        val sendTo: String? = null,
        @Enumerated(value = EnumType.STRING)
        val status: TaskStatus? = null,
        var times: Int? = null,
        @Column(columnDefinition = "TEXT")
        val msg: String? = null,
        val orderId: String? = null
) : BaseEntity(), Serializable
