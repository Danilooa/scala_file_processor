package br.com.danilooa.scala.file.processor

import scala.util.Try

case class Layout(code: String)

object Layout {

  val None = new Layout("000")
  val Salesman = new Layout("001")
  val Customer = new Layout("002")
  val Sale = new Layout("003")

  def apply(code: String): Layout = {
    val nonNullCode = Try(code.substring(0, 3)).getOrElse("000")
    nonNullCode match {
      case Salesman.code => Salesman
      case Customer.code => Customer
      case Sale.code => Sale
      case _ => None
    }
  }
}

case class Customer(cnpj: String, name: String, businessArea: String)

object Customer {
  val None = Customer("", "", "")
}

case class Salesman(cpf: String, name: String, salary: BigDecimal)

object Salesman {
  val None = Salesman("", "", BigDecimal(0))
}

case class Sale(id: Int, items: List[SaleItem], salesManName: String) {
  val total = items.foldLeft(BigDecimal(0))(_ + _.total)
}

object Sale {
  val None = Sale(0, Nil, "")
}

case class SaleItem(id: Int, quantity: Int, price: BigDecimal) {
  val total = quantity * price
}

object SaleItem {
  val None = SaleItem(0, 0, BigDecimal(0))
}

case class Row(layout: Layout, customer: Customer, salesman: Salesman, sale: Sale)

object Row {
  val None = Row(Layout.None, Customer.None, Salesman.None, Sale.None)
}