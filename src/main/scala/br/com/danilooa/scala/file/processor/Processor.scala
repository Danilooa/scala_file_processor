package br.com.danilooa.scala.file.processor

case class Report(clientsAmount: Int,
                  salesmanAmount: Int,
                  mostExpensiveSaleId: Int,
                  totalBySalesman: Map[String, BigDecimal])

object Report {

  def apply(previous: Report): Report = {
    previous
  }

  def apply() = Report(0, 0, 0, Map())

}
