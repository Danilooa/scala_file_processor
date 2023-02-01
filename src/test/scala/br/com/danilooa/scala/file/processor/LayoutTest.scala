package br.com.danilooa.scala.file.processor

import org.scalatest._
import flatspec._
import matchers._

class LayoutTest extends AnyFlatSpec with should.Matchers {

  "None Layout " should "be returned from Strings shorter than 3" in {
    Layout("01") should be(Layout.None)
    Layout(null) should be(Layout.None)
    Layout("") should be(Layout.None)
  }

  "Strings started with 001 " should "be Salesmen" in {
    Layout("001") should be(Layout.Salesman)
  }

  "Strings started with 002 " should "be Customers" in {
    Layout("002") should be(Layout.Customer)
  }

  "Strings started with 003 " should "be Sales" in {
    Layout("003") should be(Layout.Sale)
  }

}
