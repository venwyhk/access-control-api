package com.github.leon.aci.domain

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "aci_role_permission")
@DynamicUpdate
@DynamicInsert
data class RolePermission(

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "permission_id")
        val permission: Permission? = null,


        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "aci_role_permission_rule", joinColumns = [(JoinColumn(name = "role_permission_id"))],
                inverseJoinColumns = [(JoinColumn(name = "rule_id"))])
        val rules: MutableList<Rule> = mutableListOf()

) : BaseEntity(), Serializable
