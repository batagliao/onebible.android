package com.claraboia.bibleandroid.infrastructure

/**
 * Created by lucas.batagliao on 21/11/2017.
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