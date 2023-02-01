package br.com.danilooa.scala.file.processor

case class Report(clientsAmount: Int,
                  salesmanAmount: Int,
                  mostExpensiveSale: Sale,
                  totalsBySalesman: Map[String, BigDecimal]) {

  val worstSalesman = {
    val nonEmptyTotals = if (totalsBySalesman.isEmpty) Map("" -> BigDecimal(0)) else totalsBySalesman
    nonEmptyTotals
      .reduce((previous, current) => if (previous._2 < current._2) previous else current)
      ._1
  }
}

object Report {

  val None = Report(0, 0, Sale.None, Map())

  def apply(rows: List[Row]): Report = {
    rows.foldLeft(Report.None)((report, row) => {
      row.layout match {
        case Layout.Customer =>
          report.copy(clientsAmount = report.clientsAmount + 1)
        case Layout.Salesman =>
          report.copy(salesmanAmount = report.salesmanAmount + 1)
        case Layout.Sale =>
          val reportSale = report.mostExpensiveSale
          val mostExpensiveSale = if (row.sale.total > reportSale.total) row.sale else reportSale
          val salesmanName = row.sale.salesManName
          val currentTotalBySalesman = report.totalsBySalesman getOrElse(salesmanName, BigDecimal(0))
          val totalBySalesman = (salesmanName, currentTotalBySalesman + row.sale.total)
          report.copy(mostExpensiveSale = mostExpensiveSale, totalsBySalesman = report.totalsBySalesman + totalBySalesman)
        case _ =>
          report
      }
    })
  }
}
