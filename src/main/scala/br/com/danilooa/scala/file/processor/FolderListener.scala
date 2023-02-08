package br.com.danilooa.scala.file.processor

import java.nio.file.{FileSystems, Path, Paths, StandardWatchEventKinds}
import scala.language.postfixOps;

trait FileProcessor {
  def process(file: Path): Unit
}

class FolderWatcher {

  private var running = false

  def isRunning(): Boolean = {
    running
  }

  def run(folderPath: String, fileProcessor: FileProcessor): Unit = {
    var mustEndProcess = false

    val datFileRegex = """(.*\.DAT$)""" r
    val endFileName = """(^END.DAT$)""" r

    val dir = Paths.get(folderPath)
    val watcher = FileSystems.getDefault().newWatchService()
    val key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

    while (!mustEndProcess) {
      running = true
      watcher.take().pollEvents().forEach(watchEvent => {
        val file = dir.resolve(watchEvent.context().asInstanceOf[Path])
        file.getFileName.toString match {
          case datFileRegex(fileName) => fileProcessor.process(file)
          case endFileName(_) => mustEndProcess = true
        }
      })
      key.reset()
    }
  }

}
