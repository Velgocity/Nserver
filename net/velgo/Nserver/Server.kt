package net.velgo.Nserver

import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.io.IOException
import java.net.InetSocketAddress
import java.io.File
import java.io.InputStream


class Server (port : Int, name : String) {
    private val server: HttpServer = HttpServer.create(InetSocketAddress(name, port), 0)
    private val Context: HttpContext = server.createContext("/")
    fun response(Text : String, Path : String) {
        server.createContext("/NserverCONNECT/$Path.srx", MyHandler(Text))
        server.executor = null
    }

    fun resrun(Text : String, Path : String) {
        server.createContext("/NserverCONNECT/$Path.srx", MyHandler(Text))
        server.executor = null
        server.start()
    }

    fun ResponseFILE(FilePath : String, Path : String, FileExc : String = "srx") {
        val inputStreamFileText: InputStream = File(FilePath).inputStream()
        val inputFileText = inputStreamFileText.bufferedReader().use { it.readText() }
        server.createContext("/NserverCONNECT/$Path.${FileExc}", MyHandler(inputFileText))
        server.executor = null
    }

    fun run() {
        server.start()
    }

    internal class MyHandler (TextRes : String) : HttpHandler {
        private val TextResPub : String = TextRes
        @Throws(IOException::class)
        override fun handle(t: HttpExchange) {
            val response = TextResPub.toByteArray()
            t.sendResponseHeaders(200, response.size.toLong())
            val os = t.responseBody
            os.write(response)
            os.close()
        }
    }

}
