package br.com.danilooa.scala.file.processor

import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should
import org.scalatest.time.{Milliseconds, Seconds, Span}

import java.io.File
import java.nio.file.{Files, Path, Paths}
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FolderWatcherTest extends AnyFlatSpecLike with should.Matchers with BeforeAndAfter with Eventually {

  private val filesToRemove = ArrayBuffer[String]()

  after {
    filesToRemove.foreach( fileName => {
      new File(fileName).delete()
    })
  }

  "FileListener" should " run the process when a new .DAT file appears" in {

    val inputFileName = FileUtilsForTests.randomFileName("FolderListenerTest_Input_", ".DAT", 50)

    val fileProcessor = new FileProcessor {
      var hasFoundInputFile = false

      override def process(file: String, outputDir: String): Unit = {
        val onlyFileName = Paths.get(file).getFileName.toString
        hasFoundInputFile = hasFoundInputFile || (inputFileName == onlyFileName)
      }
    }

    val folderWatcher = new FolderWatcher()

    val future = Future {
      folderWatcher.run(FileUtilsForTests.inDir, FileUtilsForTests.outDir, fileProcessor)
    }

    while (!folderWatcher.isRunning()) {
      future onComplete {
        case _ =>
      }
    }

    val copiedInputFile = FileUtilsForTests.pathOfAnyInputFile(inputFileName)
    filesToRemove += copiedInputFile
    Files.copy(
      Paths.get(FileUtilsForTests.pathOfAnySampleFile("EMPTY.DAT")),
      Paths.get(copiedInputFile)
    )

    eventually(timeout(Span(5, Seconds)), interval(Span(500, Milliseconds))) {
      fileProcessor.hasFoundInputFile should be(true)
    }

  }

}
