package br.com.danilooa.scala.file.processor

import scala.util.Try

case class Customer(cnpj: String,
                    name: String,
                    businessArea: String)

object Customer {
  val None = new Customer("", "", "")

  def apply(string: String): Customer = {
    if (Layout(string) != Layout.Types.Customer) return None
    val strings = Try(string.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, cnpj_, name_, businessArea_) => new Customer(cnpj_, name_, businessArea_)
      case _ => None
    }
  }
}
