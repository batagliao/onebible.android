package app

import com.ximpleware.VTDGen
import org.mozilla.intl.chardet.nsDetector
import org.mozilla.intl.chardet.nsPSMDetector
import org.mozilla.intl.chardet.HtmlCharsetDetector.found
import java.io.*
import java.io.IOException
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import com.ximpleware.AutoPilot
import java.io.UnsupportedEncodingException
import java.io.FileNotFoundException
import java.io.PrintWriter
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.FileInputStream


fun main(args: Array<String>) {

    val ifile = args[0]
    val inputfile = File(ifile)

    if (!inputfile.exists()) {
        throw Exception("Arquivo de entrada não existe")
    }

    println(inputfile.canonicalPath)

    BibleVTDParser.parse(inputfile.inputStream())

    // charsetDetection(inputfile)

    // val indexFile = generateIndex(inputfile)

    // test(indexFile)

}

private fun generateIndex(input: File) : File{
    val gen = VTDGen()

    val outputfile = File(input.absolutePath.replace(".bib", ".vtd"))
    if (outputfile.exists()) {
        println("arquivo de destino será sobrescrito")
        // charsetDetection(outputfile)
    }

    if (gen.parseFile(input.absolutePath, true)) {
        println("escrevendo index")
        gen.writeIndex(outputfile.absolutePath)
        println("pronto")
        // convertToUtf8(outputfile)
    } else {
        println("não foi possível fazer o parse")
    }

    return outputfile
}

private fun charsetDetection(inputFile: File) {
    val detector = nsDetector(nsPSMDetector.ALL)
    detector.Init {
        println("CHARSET = $it")
    }

    BufferedInputStream(inputFile.inputStream()).use {
        val buf = ByteArray(1024)
        var len: Int
        var done = false
        var isAscii = true

        do {
            len = it.read(buf, 0, buf.size)
            if (len < 0) break

            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = detector.isAscii(buf, len)

            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done)
                done = detector.DoIt(buf, len, false)
        } while (len > -1)

        detector.DataEnd()

        if (isAscii) {
            println("CHARSET = ASCII")
            found = true
        }
    }
}

private fun convertToUtf8(file: File){
    println("Convertendo em utf8")

    val input = InputStreamReader(BufferedInputStream(file.inputStream(), 8192), "UTF8")
    val output = OutputStreamWriter(FileOutputStream("${file.absoluteFile}.utf8.txt"), "UTF-8")

    val data = CharArray(1024)
    var total = 0
var count = 0
    do{
        count = input.read(data)
        total += count

        if(count > 0){
            output.write(data, 0, count)
        }
    }while (count > 0)

    output.flush()
    output.close()
    input.close()

    val fis = file.inputStream()
    val reader = InputStreamReader(fis, "CP1250")

    val fos = file.outputStream()
    val writer = OutputStreamWriter(fos, "UTF-8")


    val buffer = CharArray(1024)
    do{
        val i = reader.read(buffer,0, buffer.size)
        if(i == -1) break
        writer.write(buffer, 0, buffer.size)
    }while(i > -1)
    println("feito")
}

private fun test(indexFile: File){
    val gen = VTDGen()
    val nav = gen.loadIndex(indexFile.absolutePath)
    val ap = AutoPilot(nav)
    ap.selectXPath("/bible/title/text()")
    val i = ap.evalXPath()
    val title = nav.toString(i)

    println(title)
}