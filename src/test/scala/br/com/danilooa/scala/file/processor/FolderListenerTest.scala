package br.com.danilooa.scala.file.processor

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import java.nio.file.{Files, Paths}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FolderListenerTest extends AnyFlatSpec with should.Matchers with Eventually with MockFactory {

  "FileListener" should " run the process when a new .DAT file appears" in {
    val folderListener = mock[FolderListener]

    val inputFileName = FileUtilsForTests.randomFileName("FolderListenerTest_Input_", ".DAT", 50)

    eventually {
      (folderListener.reportFile _).expects(inputFileName)
    }

    Future {
      FolderListener.run(FileUtilsForTests.inDir, folderListener)
    } onComplete {
      case _ =>
    }

    //    val content = List(
    //      "001ç1234567891234çDiegoç50000",
    //      "001ç3245678865434çRenatoç40000.99",
    //      "002ç2345675434544345çJosedaSilvaçRural",
    //      "002ç2345675433444345çEduardoPereiraçRural",
    //      "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
    //      "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"
    //    )
    //
    //
    //    val name: String = Paths.get(filesInputDir, inputFileName).toString
    //    val writer = new PrintWriter(name)
    //    writer.write(content.mkString("\n"))
    //    writer.close()

    Files.copy(
      Paths.get(FileUtilsForTests.pathOfAnySampleFile("EMPTY.DAT")),
      Paths.get(FileUtilsForTests.pathOfAnyInputFile(inputFileName))
    )
  }

}
