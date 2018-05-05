package app

import com.ximpleware.AutoPilot
import com.ximpleware.VTDGen
import com.ximpleware.VTDNav
import java.io.InputStream

object BibleVTDParser {

    val gen = VTDGen()
    lateinit var nav: VTDNav
    lateinit var ap: AutoPilot

    fun parse(istream: InputStream) {
        nav = gen.loadIndex(istream)
        ap = AutoPilot(nav)

        ap.selectXPath("/*")
        val bible_idx = ap.evalXPath()
        if (nav.toString(bible_idx) == "bible") {
            // continue
        }

        if (nav.toElement(VTDNav.FIRST_CHILD, "title")) {
            val text = nav.toString(nav.text)
            println(text)
        }

        if (nav.toElement(VTDNav.NEXT_SIBLING, "b")) {
            do {
                parseBook()
            } while (nav.toElement(VTDNav.NEXT_SIBLING, "b"))
        }
    }

    private fun parseBook() {
        nav.push()
        var order = ""
        if (nav.attrCount > 0) {
            order = nav.toString(nav.getAttrVal("o"))
            println("book $order")
        }
        if (nav.toElement(VTDNav.FIRST_CHILD, "c")) {
            do {
                println("book $order")
                parseChapter()
            } while (nav.toElement(VTDNav.NEXT_SIBLING, "c"))
        }
        nav.pop()
    }

    private fun parseChapter() {
        nav.push()
        if (nav.attrCount > 0) {
            val order = nav.toString(nav.getAttrVal("n"))
            println("chapter $order")
        }
        if (nav.toElement(VTDNav.FIRST_CHILD, "v")) {
            parseVerses()
        }
        nav.pop()
    }

    private fun parseVerses() {
        nav.push()
        do {
            val order = nav.toString(nav.getAttrVal("n"))
            val itext = nav.text
            var text = ""
            if(itext > -1) {
                text = nav.toString(nav.text)
            }
            println("$order - $text")
        } while (nav.toElement(VTDNav.NEXT_SIBLING, "v"))

        nav.pop()
    }
}