package br.com.danilooa.scala.file.processor

import org.scalatest._
import flatspec._
import matchers._

class ExampleSpec extends AnyFlatSpec with should.Matchers {

  "None Layout " should "be returned from Strings shorter than 3" in {
    Layout("01") should be(Layout.Types.None)
    Layout(null) should be(Layout.Types.None)
    Layout("") should be(Layout.Types.None)
  }

  "Strings started with 001 " should "be Salesmen" in {
    Layout("001") should be(Layout.Types.Salesman)
  }

  "String started with 002 " should "be Customers" in {
    Layout("002") should be(Layout.Types.Customer)
  }

  "Strings started with 003 " should "be Sale" in {
    Layout("003") should be(Layout.Types.Sale)
  }

}
