package br.com.danilooa.scala.file.processor

import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should

class OutputFileNameTest extends AnyFlatSpecLike with should.Matchers {

  "The output file" should " follow the pattern {flat_file_name}.done.dat" in {
    val inputFileNameWithoutExtension = FileUtilsForTests.randomFileName("FolderListenerTest_Input_", "", 50)
    val inputFileName = FileUtilsForTests.pathOfAnyInputFile(s"$inputFileNameWithoutExtension.dat")

    val outputFilePath = FileUtilsForTests.pathOfAnyOutFile(s"$inputFileNameWithoutExtension.done.dat")

    OutputFilePath.renameInputFile(inputFileName, FileUtilsForTests.outDir) should be(outputFilePath)
  }

}
