package com.claraboia.bibleandroid.infrastructure

import java.util.*

/**
 * Created by lucas.batagliao on 14/10/2016.
 */
open class Event<eventArg : EventArg> {
    private val handlers = ArrayList<((eventArg) -> Unit)>()

    operator fun plusAssign(handler: (eventArg) -> Unit) {
        handlers.add(handler)
    }

    fun minusAssign(handler: (eventArg) -> Unit) {
        handlers.remove(handler)
    }

    fun invoke(value: eventArg){
        for(handler in handlers){
            handler(value)
        }
    }
}