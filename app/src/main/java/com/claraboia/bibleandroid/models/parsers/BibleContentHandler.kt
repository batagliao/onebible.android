package com.claraboia.bibleandroid.models.parsers

import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.Chapter
import com.claraboia.bibleandroid.models.Verse
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class BibleContentHandler : DefaultHandler() {

    val Bible : Bible = Bible()

    private var tempVal: String = ""
    private var elementOn: Boolean = false
    private var currentBook: Book? = null
    private var currentChapter: Chapter? = null
    private var currentVerse: Verse? = null

    override fun startElement(uri: String?, localName: String?, qName: String?, atts: Attributes?) {
        var tempVal = ""

        if(localName.equals("bible", true)){
        }

        if (localName.equals("title", true)) {
            elementOn = true
        }

        if (localName.equals("b", true)) { //book
            currentBook = Book()
            val order = Integer.parseInt(atts!!.getValue("o")) //order
            currentBook!!.BookOrder = order
        }

        if (localName.equals("c", true)) { //chapter
            currentChapter = Chapter()
            val number = Integer.parseInt(atts!!.getValue("n")) //number
            currentChapter!!.ChapterOrder = number
        }

        if (localName.equals("v", true)) { //verse
            elementOn = true
            currentVerse = Verse()
            val number = Integer.parseInt(atts!!.getValue("n")) //number
            currentVerse!!.VerseOrder = number
        }
    }


    override fun characters(ch: CharArray?, start: Int, length: Int) {
        if (elementOn) {
            tempVal = String(ch as CharArray, start, length)
            elementOn = false
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        elementOn = false

        if (localName.equals("title", true)) {
            this.Bible.Title = tempVal
        }

        if (localName.equals("b", true)) { //book
            this.Bible.Books.add(currentBook!!)
            currentBook = null
        }

        if(localName.equals("c", true)) { //chapter
            currentBook!!.Chapters.add(currentChapter!!)
            currentChapter = null
        }

        if(localName.equals("v", true)) { //verse
            currentChapter!!.Verses.add(currentVerse!!)
            currentVerse!!.Text = tempVal;
            currentVerse = null
        }
    }





}