package br.com.danilooa.scala.file.processor

case class Report(clientsAmount: Int,
                  salesmanAmount: Int,
                  mostExpensiveSaleId: Int,
                  totalBySalesman: Map[String, BigDecimal])

object Report {

}
