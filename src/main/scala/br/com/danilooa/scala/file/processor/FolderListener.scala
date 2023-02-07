package br.com.danilooa.scala.file.processor

import java.io.File
import java.nio.file.Files
import scala.language.postfixOps;

case class ListeningResult(datFileFound: Boolean, endFileFound: Boolean, fileName: String)

object ListeningResult {
  val None = ListeningResult(false, false, "")
}

trait FolderListener {
  def reportFile(fileName: String): Unit = {
    println(fileName)
  }
}

object FolderListener {

  def run(folderPath: String, listener: FolderListener, lifeSpan: Long = -1): Unit = {
    val initTime = System.currentTimeMillis()
    var mustEndProcess = false

    val dir = new File(folderPath)
    val datFileRegex = """(.*\.DAT$)""" r
    val endFileName = """(^END.DAT$)""" r

    while (!mustEndProcess) {
      val listeningResult = dir
        .listFiles()
        .foldLeft(ListeningResult.None)((result, file) => {
          if (result != ListeningResult.None) return result
          val fileName = file.getName
          fileName match {
            case datFileRegex(_) => ListeningResult(true, false, fileName)
            case endFileName(_) => ListeningResult(false, true, fileName)
            case _ => ListeningResult.None
          }
        })

      val endFileFound = listeningResult.endFileFound
      val lifeSpanGone = if (lifeSpan < 0) false else (System.currentTimeMillis() - initTime) > lifeSpan
      mustEndProcess = endFileFound || lifeSpanGone
      if (listeningResult.datFileFound) {
        listener.reportFile(listeningResult.fileName)
        new File(folderPath + "/" + listeningResult.fileName).renameTo(new File(folderPath + "/" + listeningResult.fileName + ".DOING"))
      }
    }

  }
}
