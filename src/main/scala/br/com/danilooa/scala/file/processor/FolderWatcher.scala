package br.com.danilooa.scala.file.processor

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{FileSystems, Path, Paths, StandardWatchEventKinds}
import scala.concurrent.Future
import scala.io.Source
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

object RegexUtils {
  val datFileRegex = """(?i)(.*\.DAT$)""" r
  val doneDatExtension = ".done.dat"
}

trait FileProcessor {
  def process(file: String, outputDir: String): Unit = {
    Future {
      Report(Source.fromFile(file).getLines().map(RowParser(_)).toList)
    } flatMap (r => Future {
      val outputFilePath = OutputFilePath.renameInputFile(file, outputDir)
      val writer = new BufferedWriter(new FileWriter(outputFilePath))
      writer.write(r.toString)
      writer.close()
    }) foreach (_ => {
      new File(file).delete()
    })
  }
}

object OutputFilePath {
  def renameInputFile(inputFileWholePath: String, outputDirPath: String): String = {
    val onlyInputFileName = Paths.get(inputFileWholePath).getFileName.toString
    val onlyOutputFileName = onlyInputFileName.take(onlyInputFileName.lastIndexOf(".")) + RegexUtils.doneDatExtension
    Paths.get(outputDirPath, onlyOutputFileName).toString
  }
}

class FolderWatcher {

  private var running = false

  def isRunning(): Boolean = {
    this.running
  }

  def run(inputDir: String, outputDir: String, fileProcessor: FileProcessor = new FileProcessor {}): Unit = {

    val dir = Paths.get(inputDir)
    val watcher = FileSystems.getDefault().newWatchService()
    val key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

    while (true) {
      this.running = true
      watcher.take().pollEvents().forEach(watchEvent => {
        val file = dir.resolve(watchEvent.context().asInstanceOf[Path])
        file.getFileName.toString match {
          case RegexUtils.datFileRegex(fileName) => fileProcessor.process(fileName, outputDir)
          case _ => println(s"Ignoring file ${file.getFileName}")
        }
      })
      key.reset()
    }
  }

  def main(args: Array[String]): Unit = {

  }
}
