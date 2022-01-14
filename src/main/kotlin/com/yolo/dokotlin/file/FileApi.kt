package com.yolo.dokotlin.file

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileInputStream
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

@RequestMapping("/api")
@RestController
class FileApi {


    @GetMapping("/files/download")
    fun download(response: HttpServletResponse): Unit {

        val path = "/Users/limjun-young/workspace/privacy/dev/test/plaintext.txt"

        val fis = FileInputStream(path)

        var byte: ByteArray = byteArrayOf()
        byte = fis.readBytes()

        response.contentType = MediaType.APPLICATION_OCTET_STREAM.toString()
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode("plaintext.txt", "UTF-8") + "\";")
        response.setHeader("Content-Transfer-Encoding", "binary")

        response.outputStream.write(byte)
        response.outputStream.flush()
        response.outputStream.close()
    }
}
