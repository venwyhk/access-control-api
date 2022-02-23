package com.github.leon.bean


import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl

import java.util.HashSet

object JpaBeanUtil {

    fun copyNonNullProperties(src: Any, target: Any) {
        BeanUtils.copyProperties(src, target, *getNullPropertyNames(src))
    }

    private fun getNullPropertyNames(source: Any): Array<String> {
        val src = BeanWrapperImpl(source)
        val pds = src.propertyDescriptors

        val emptyNames = HashSet<String>()
        for (pd in pds) {
            when {
                pd.propertyType.isAssignableFrom(List::class.java) -> emptyNames.add(pd.name)
                pd.propertyType.isAssignableFrom(MutableList::class.java) -> emptyNames.add(pd.name)
                src.getPropertyValue(pd.name) == null -> emptyNames.add(pd.name)
            }
        }
        val result = arrayOfNulls<String>(emptyNames.size)
        return emptyNames.toTypedArray()
    }

}
