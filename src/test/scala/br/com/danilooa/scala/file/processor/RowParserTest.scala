package br.com.danilooa.scala.file.processor

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class RowParserTest extends AnyFlatSpec with should.Matchers {

  "RowParser" should "parse row strings from different layouts" in {
    val stringRows = List(
      "001ç1234567891234çDiegoç50000",
      "001ç3245678865434çRenatoç40000.99",
      "002ç2345675434544345çJosedaSilvaçRural",
      "002ç2345675433444345çEduardoPereiraçRural",
      "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
      "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"
    )

    val diegoSaleItems = List(SaleItem(1, 10, 100), SaleItem(2, 30, 2.50), SaleItem(3, 40, 3.10))
    val renatoSaleItems = List(SaleItem(1, 34, 10), SaleItem(2, 33, 1.50), SaleItem(3, 40, 0.10))
    val expectedRows = List(
      Row(Layout.Salesman, Customer.None, Salesman("1234567891234", "Diego", 50000), Sale.None),
      Row(Layout.Salesman, Customer.None, Salesman("3245678865434", "Renato", 40000.99), Sale.None),
      Row(Layout.Customer, Customer("2345675434544345", "JosedaSilva", "Rural"), Salesman.None, Sale.None),
      Row(Layout.Customer, Customer("2345675433444345", "EduardoPereira", "Rural"), Salesman.None, Sale.None),
      Row(Layout.Sale, Customer.None, Salesman.None, Sale(10, diegoSaleItems, "Diego")),
      Row(Layout.Sale, Customer.None, Salesman.None, Sale(8, renatoSaleItems, "Renato"))
    )
    val rows = stringRows.map(RowParser(_))
    rows should be(expectedRows)
  }

}
