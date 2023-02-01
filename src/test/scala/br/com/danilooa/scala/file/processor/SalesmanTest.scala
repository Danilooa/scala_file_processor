package br.com.danilooa.scala.file.processor

import org.scalatest.flatspec._
import org.scalatest.matchers._

class SalesmanTest extends AnyFlatSpec with should.Matchers {

  "A salesman string " should "be converted to Salesman" in {
    val salesman = RowParser("001ç1234567891234çDiegoç50000").salesman
    salesman should be(Salesman("1234567891234", "Diego", BigDecimal(50000)))
  }

}