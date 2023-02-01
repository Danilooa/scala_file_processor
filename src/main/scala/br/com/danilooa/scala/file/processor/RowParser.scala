package br.com.danilooa.scala.file.processor

import scala.util.Try

object RowParser {

  def apply(rowString: String): Row = {
    val layout = Layout(rowString)
    layout match {
      case Layout.Customer => Row(layout, parseCustomer(rowString), Salesman.None, Sale.None)
      case Layout.Salesman => Row(layout, Customer.None, parseSalesman(rowString), Sale.None)
      case Layout.Sale => Row(layout, Customer.None, Salesman.None, parseSale(rowString))
      case _ => Row.None
    }
  }

  private def parseCustomer(rowString: String): Customer = {
    val strings = Try(rowString.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, cnpj_, name_, businessArea_) => new Customer(cnpj_, name_, businessArea_)
      case _ => Customer.None
    }
  }

  private def parseSalesman(string: String): Salesman = {
    val strings = Try(string.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, cpf, name, salary) => new Salesman(cpf, name, BigDecimal(salary))
      case _ => Salesman.None
    }
  }

  private def parseSaleItem(string: String): SaleItem = {
    val strings = Try(string.split("-").toList).getOrElse(Nil)
    strings match {
      case List(id, quantity, price) => new SaleItem(id.toInt, quantity.toInt, BigDecimal(price))
      case _ => SaleItem.None
    }
  }

  private def parseSale(string: String): Sale = {
    val strings = Try(string.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, id, itemsString, salesmanName) =>
        val items = itemsString
          .replaceAll("[\\[\\]]", "")
          .split(",")
          .map(parseSaleItem(_))
          .toList
        new Sale(id.toInt, items, salesmanName)
      case _ =>
        Sale.None
    }
  }

}
