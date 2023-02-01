package br.com.danilooa.scala.file.processor

import scala.util.Try

case class Sale(id: Int,
                items: List[SaleItem],
                salesManName: String) {
  val total = items.foldLeft(BigDecimal(0))(_ + _.total)
}

case class SaleItem(id: Int,
                    quantity: Int,
                    price: BigDecimal) {
  val total = quantity * price
}

object Sale {

  val None = new Sale(0, Nil, "")

  def apply(string: String): Sale = {
    if (Layout(string) != Layout.Types.Sale) return None
    val strings = Try(string.split("ç").toList).getOrElse(Nil)
    strings match {
      case List(_, id, itemsString, salesmanName) =>
        val items = itemsString.replaceAll("[\\[\\]]", "").split(",").map(SaleItem(_)).toList
        new Sale(id.toInt, items, salesmanName)
      case _ =>
        None
    }
  }

}

object SaleItem {

  val None = new SaleItem(0, 0, BigDecimal(0))

  def apply(string: String): SaleItem = {
    val strings = Try(string.split("-").toList).getOrElse(Nil)
    strings match {
      case List(id, quantity, price) => new SaleItem(id.toInt, quantity.toInt, BigDecimal(price))
      case _ => None
    }
  }

}