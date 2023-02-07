package br.com.danilooa.scala.file.processor

import java.io.File
import scala.util.Random

object FileUtilsForTests {

  val resourcesDir = new File("src/test/resources").getAbsoluteFile

  val inDir = resourcesDir + "/data/in"

  val outDir = resourcesDir + "/data/out"

  val samplesDir = resourcesDir + "/data/samples"

  def pathOfAnyInputFile(fileName: String) = inDir + "/" + fileName

  def pathOfAnyOutFile(fileName: String) = outDir + "/" + fileName

  def pathOfAnySampleFile(fileName: String) = samplesDir + "/" + fileName

  def randomFileName(prefix: String, postfix: String, length: Int) = {
    val randomLength = length - prefix.length
    prefix + (for (_ <- 0 to randomLength) yield Random.alphanumeric.take(1).mkString).mkString + postfix
  }

}
