package br.com.danilooa.scala.file.processor

import scala.util.Try

case class Salesman(cpf: String,
                    name: String,
                    salary: BigDecimal)

object Salesman {
  val None = new Salesman("", "", BigDecimal(0))

  def apply(string: String): Salesman = {
    if (Layout(string) != Layout.Types.Salesman) return None
    val strings = Try(string.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, cpf, name, salary) => new Salesman(cpf, name, BigDecimal(salary))
      case _ => None
    }
  }
}
