package com.claraboia.bibleandroid.models.parsers

import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.Chapter
import com.claraboia.bibleandroid.models.Verse
import com.ximpleware.AutoPilot
import com.ximpleware.VTDGen
import com.ximpleware.VTDNav
import java.io.InputStream

object BibleVTDParser {

    val gen = VTDGen()
    lateinit var nav: VTDNav
    lateinit var ap: AutoPilot

    fun parse(istream: InputStream): Bible {
        nav = gen.loadIndex(istream)
        ap = AutoPilot(nav)

        ap.selectXPath("/*")
        val bible_idx = ap.evalXPath()
        if (nav.toString(bible_idx) == "bible") {
            // continue
        }

        val bible = Bible()

        if (nav.toElement(VTDNav.FIRST_CHILD, "title")) {
            val text = nav.toString(nav.text)
            bible.title = text
        }

        if (nav.toElement(VTDNav.NEXT_SIBLING, "b")) {
            do {
                bible.books.add(parseBook())
            } while (nav.toElement(VTDNav.NEXT_SIBLING, "b"))
        }
        return bible
    }

    private fun parseBook(): Book {
        nav.push()
        val book = Book()
        if (nav.attrCount > 0) {
            val order = nav.toString(nav.getAttrVal("o"))
            book.bookOrder = order.toInt()
        }
        if (nav.toElement(VTDNav.FIRST_CHILD, "c")) {
            do {
                book.chapters.add(parseChapter())
            } while (nav.toElement(VTDNav.NEXT_SIBLING, "c"))
        }
        nav.pop()
        return book
    }

    private fun parseChapter(): Chapter {
        nav.push()
        val chapter = Chapter()
        if (nav.attrCount > 0) {
            val order = nav.toString(nav.getAttrVal("n"))
            chapter.chapterOrder = order.toInt()
        }
        if (nav.toElement(VTDNav.FIRST_CHILD, "v")) {
            chapter.verses = parseVerses()
        }
        nav.pop()
        return chapter
    }

    private fun parseVerses(): MutableList<Verse> {
        nav.push()
        val verses = mutableListOf<Verse>()
        do {
            val verse = Verse()

            val order = nav.toString(nav.getAttrVal("n"))
            val itext = nav.text
            var text = ""
            if (itext > -1) {
                text = nav.toString(nav.text)
            }

            verse.verseOrder = order.toInt()
            verse.text = text
            verses.add(verse)
        } while (nav.toElement(VTDNav.NEXT_SIBLING, "v"))

        nav.pop()
        return verses
    }
}