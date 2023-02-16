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
      println(s"Processing $file")
      val report = Report(Source.fromFile(file).getLines().map(RowParser(_)).toList)
      println(s"Finished to process $file")
      report
    } flatMap (r => Future {
      val outputFilePath = OutputFilePath.renameInputFile(file, outputDir)
      println(s"Writing report $outputFilePath")
      val writer = new BufferedWriter(new FileWriter(outputFilePath))
      writer.write(r.toString)
      writer.close()
      println(s"Report $outputFilePath wrote")
    }) foreach (_ => {
      println(s"Deleting $file")
      new File(file).delete()
      println(s"Deleted $file")
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

  private def turnOn(): Unit = {
    if (running) return;
    this.running = true;
    println("FolderWatcher is running")
  }

  def run(inputDir: String, outputDir: String, fileProcessor: FileProcessor = new FileProcessor {}): Unit = {

    val dir = Paths.get(inputDir)
    val watcher = FileSystems.getDefault().newWatchService()
    val key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

    while (true) {
      turnOn()
      watcher.take().pollEvents().forEach(watchEvent => {
        val file = dir.resolve(watchEvent.context().asInstanceOf[Path])
        println(s"FIle ${file.getFileName} has detected")
        file.getFileName.toString match {
          case RegexUtils.datFileRegex(_) => fileProcessor.process(file.toString, outputDir)
          case _ => println(s"Ignoring file ${file.getFileName}")
        }
      })
      key.reset()
    }
  }

}
