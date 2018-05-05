package com.claraboia.bibleandroid.models.parsers

import com.claraboia.bibleandroid.models.Bible
import com.claraboia.bibleandroid.models.Book
import com.claraboia.bibleandroid.models.Chapter
import com.claraboia.bibleandroid.models.Verse
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

object BiblePullParser {
    fun parse(inputStream: InputStream): Bible {
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false
        factory.isValidating = false
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)

        val pullparser = factory.newPullParser()
        pullparser.setInput(inputStream, null)

        val bible = Bible()

        var currentBook: Book? = null
        var currentChapter: Chapter? = null

        var eventType = pullparser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT){

            when(eventType){
                XmlPullParser.START_TAG -> {
                    val name = pullparser.name
                    // title
                    if(name == "title"){
                        bible.title = pullparser.nextText()
                    }
                    // book start
                    else if(name == "b"){
                        currentBook = Book()
                        currentBook.bookOrder = pullparser.getAttributeValue(null, "o").toInt()
                    }
                    // chapter start
                    else if(name == "c"){
                        currentChapter = Chapter()
                        currentChapter.chapterOrder = pullparser.getAttributeValue(null, "n").toInt()
                    }
                    // verse start
                    else if(name == "v"){
                        val v = Verse()
                        v.verseOrder = pullparser.getAttributeValue(null, "n").toInt()
                        v.text = pullparser.nextText()
                        currentChapter!!.verses.add(v)
                    }
                }

                XmlPullParser.END_TAG -> {
                    val name = pullparser.name
                    if(name == "b"){
                        bible.books.add(currentBook!!)
                        currentBook = null
                    }
                    else if (name == "c"){
                        currentBook!!.chapters.add(currentChapter!!)
                        currentChapter = null
                    }
                }

                XmlPullParser.TEXT -> {

                }

            }
            eventType = pullparser.next()
        }
        return bible
    }
}