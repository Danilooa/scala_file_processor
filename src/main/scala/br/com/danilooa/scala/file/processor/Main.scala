package br.com.danilooa.scala.file.processor

object Main {
  def main(args: Array[String]): Unit = {
    val inDir = "/home/jexperts/data/in"
    val outDir = "/home/jexperts/data/out"
    new FolderWatcher().run(inDir, outDir)
  }
}
