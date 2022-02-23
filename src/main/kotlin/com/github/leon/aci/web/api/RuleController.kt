package com.github.leon.aci.web.api

import com.github.leon.aci.domain.Rule
import com.github.leon.aci.service.rule.RuleService
import com.github.leon.bean.JpaBeanUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping(value = ["/v1/rule"])
class RuleController {

    @Autowired
    private val ruleService: RuleService? = null

    @GetMapping
    fun index(pageable: Pageable): ResponseEntity<Page<Rule>> {
        return ResponseEntity.ok(ruleService!!.findAll(pageable))
        //return ResponseEntity.responseEntityOk(ruleService.findBySecurity(request.getMethod(), request.getRequestURI(), HashMap.ofAll(request.getParameterMap()), pageable));

    }


    @GetMapping("{id}")
    operator fun get(@PathVariable id: Long): ResponseEntity<Rule> {
        return ResponseEntity.ok(ruleService!!.findOne(id))
    }

    @PostMapping
    fun save(@RequestBody rule: Rule, request: HttpServletRequest): ResponseEntity<Rule> {
        return ResponseEntity.created(URI.create(request.requestURI)).body(rule)
    }


    @PutMapping("{id}")
    fun save(@PathVariable id: Long, @RequestBody rule: Rule): ResponseEntity<Rule> {
        val oldRule = ruleService!!.findOne(id)
        JpaBeanUtil.copyNonNullProperties(rule, oldRule)
        ruleService.save(oldRule)
        return ResponseEntity.ok(oldRule)
    }


    @DeleteMapping
    fun delete(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<*> {
        ruleService!!.deleteBySecurity(id, request.method, request.requestURI)
        return ResponseEntity.ok().build<Any>()
    }

}