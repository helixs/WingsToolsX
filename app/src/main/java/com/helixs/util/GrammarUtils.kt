package com.helixs.util

/**
 * Created by helixs on 2017/10/13.
 */
object GrammarUtils{
    fun isEmpty(list:Collection<Any>?=null):Boolean{
        return list==null||list.isEmpty()
    }
}