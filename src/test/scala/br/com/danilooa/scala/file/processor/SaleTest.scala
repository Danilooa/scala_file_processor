package br.com.danilooa.scala.file.processor

import org.scalatest.flatspec._
import org.scalatest.matchers._

class SaleTest extends AnyFlatSpec with should.Matchers {

  "A sale string " should "be converted to Sale" in {
    val sale = Sale("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego")
    val items = List(
      SaleItem(1, 10, BigDecimal(100)),
      SaleItem(2, 30, BigDecimal(2.50)),
      SaleItem(3, 40, BigDecimal(3.10))

    )
    val expected = Sale(10, items, "Diego")
    sale should be(expected)
  }

}