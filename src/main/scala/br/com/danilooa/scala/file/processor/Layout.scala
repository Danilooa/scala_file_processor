package br.com.danilooa.scala.file.processor

import scala.util.Try

case class Layout(code: String)

object Layout {

  object Types {
    val None = new Layout("000")
    val Salesman = new Layout("001")
    val Customer = new Layout("002")
    val Sale = new Layout("003")
  }

  def apply(code: String): Layout = {
    val nonNullCode = Try(code.substring(0, 3)).getOrElse("000")
    nonNullCode match {
      case Types.Salesman.code => Types.Salesman
      case Types.Customer.code => Types.Customer
      case Types.Sale.code => Types.Sale
      case _ => Types.None
    }


  }

}
