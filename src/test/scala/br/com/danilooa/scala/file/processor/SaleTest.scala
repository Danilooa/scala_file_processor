package br.com.danilooa.scala.file.processor

import org.scalatest.flatspec._
import org.scalatest.matchers._

class SaleTest extends AnyFlatSpec with should.Matchers {

  "A sale string " should "be converted to Sale" in {
    val sale = RowParser("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego").sale
    val items = List(
      SaleItem(1, 10, BigDecimal(100)),
      SaleItem(2, 30, BigDecimal(2.50)),
      SaleItem(3, 40, BigDecimal(3.10))

    )
    val expected = Sale(10, items, "Diego")
    sale should be(expected)
  }

  "SaleItem" should "return the total" in {
    val saleItem = SaleItem(0, 2, 13.3)
    saleItem.total should be(26.6)
  }

  "Sale" should "return the total" in {
    val saleItems = List(
      SaleItem(1, 2, 5),
      SaleItem(2, 1, 1),
      SaleItem(3, 5, 1.1),
      SaleItem(4, 4, 25)
    )
    val sale = Sale(1, saleItems, "")
    sale.total should be(116.5)
  }

}