package com.saintdan.framework.servlet

import org.apache.tomcat.util.http.fileupload.IOUtils
import java.io.*
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * Multiple reader for HttpServletRequest.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 07/12/2017
 * @since JDK1.8
 */
class RequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

  private var cachedBytes: ByteArrayOutputStream? = null

  @Throws(IOException::class)
  override fun getInputStream(): ServletInputStream {
    if (cachedBytes == null) {
      cacheInputStream()
    }

    return CachedServletInputStream()
  }

  @Throws(IOException::class)
  override fun getReader(): BufferedReader {
    return BufferedReader(InputStreamReader(inputStream))
  }

  @Throws(IOException::class)
  private fun cacheInputStream() {
    /* Cache the inputstream in order to read it multiple times. For
     * convenience, I use apache.commons IOUtils
     */
    cachedBytes = ByteArrayOutputStream()
    IOUtils.copy(super.getInputStream(), cachedBytes)
  }

  /* An inputstream which reads the cached request body */
  inner class CachedServletInputStream : ServletInputStream() {

    private val byteArrayInputStream: ByteArrayInputStream = ByteArrayInputStream(cachedBytes!!.toByteArray())

    init {
      /* create a new input stream from the cached request body */
    }

    @Throws(IOException::class)
    override fun read(): Int {
      return byteArrayInputStream.read()
    }

    override fun isFinished(): Boolean {
      return byteArrayInputStream.available() == 0
    }

    override fun isReady(): Boolean {
      return true
    }

    override fun setReadListener(listener: ReadListener) {
      throw RuntimeException("Not implemented")
    }
  }
}