package com.github.leon.email.domain

import com.github.leon.aci.domain.BaseEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.Entity


@Entity
@DynamicUpdate
@DynamicInsert

data class EmailServer(
        var host: String = "",
        var timeout: Int = 60000,
        var port: Int = 587,
        var alias: String = "",
        var fromAddress: String = "",
        var username: String = "",
        var password: String = ""
) : BaseEntity(), Serializable
