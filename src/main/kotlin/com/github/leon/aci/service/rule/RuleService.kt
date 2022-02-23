package com.github.leon.aci.service.rule

import arrow.core.Option
import arrow.syntax.collections.firstOption
import com.github.leon.aci.domain.Rule
import com.github.leon.aci.service.base.BaseService
import com.github.leon.aci.service.rule.access.AccessRule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service

class RuleService : BaseService<Rule, Long>() {
    val log = LoggerFactory.getLogger(RuleService::class.java)
    @Autowired
    lateinit var accessRules: List<AccessRule>

    fun findAccessRules(ruleName: String): Option<AccessRule> {
        log.debug("rule name {}", ruleName)
        return accessRules.firstOption { it.ruleName == ruleName }
    }
}
