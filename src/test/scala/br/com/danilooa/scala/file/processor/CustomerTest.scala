package br.com.danilooa.scala.file.processor

import org.scalatest._
import flatspec._
import matchers._

class CustomerTest extends AnyFlatSpec with should.Matchers {

  "A customer string " should "be converted to Customer" in {
    val customer = RowParser("002ç2345675433444345çEduardoPereiraçRural").customer
    customer should be(Customer("2345675433444345", "EduardoPereira", "Rural"))
  }

}
