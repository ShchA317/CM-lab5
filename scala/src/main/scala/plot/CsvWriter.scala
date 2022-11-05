package plot

import java.io.PrintWriter
import java.io.File
import scala.collection.mutable

object CsvWriter {
  def writeCsv(x_values: mutable.Buffer[Double], y_values: mutable.Buffer[Double], filename: String) = {
    val file = new File(filename)
    val pw = new PrintWriter(file)
    pw.write("x,y\n")
    for (i<-x_values.indices){
      pw.println(f"${x_values(i)}%.6f,${y_values(i)}%.6f")
    }
    pw.close()
  }
}