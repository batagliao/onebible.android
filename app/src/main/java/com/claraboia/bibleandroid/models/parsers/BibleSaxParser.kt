package com.claraboia.bibleandroid.models.parsers

import com.claraboia.bibleandroid.models.Bible
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParserFactory

/**
 * Created by lucas.batagliao on 12/07/2016.
 */
class BibleSaxParser {

    fun parse(inputStream: InputStream): Bible {
        var b: Bible? = null

        try {
            val parser = SAXParserFactory.newInstance().newSAXParser()
            val reader = parser.xmlReader
            val contentHandler = BibleContentHandler()
            reader.contentHandler = contentHandler
            parser.parse(inputStream, contentHandler)
            b = contentHandler.Bible
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return b!!
    }
}