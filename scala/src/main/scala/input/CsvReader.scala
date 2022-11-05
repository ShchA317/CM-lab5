package input

import scala.io.Source
import scala.collection.mutable

object CsvReader{
  val fileName = "input/input1.csv"

  val items = for {
    line <- Source.fromFile(fileName).getLines()
    entries = line.split(",")
  } yield entries


  val values: mutable.Buffer[Double] = mutable.Buffer[Double]()
  items.toList.foreach(_.toList.map(_.toDouble).foreach(values += _))

  val x_values: mutable.Buffer[Double] = mutable.Buffer[Double]()
  val y_values: mutable.Buffer[Double] = mutable.Buffer[Double]()

  var i = 0
  y_values.++=(values.filter(_ => {i+=1; i%2 == 0}))
  x_values.++=(values.filterNot(y_values.contains(_)))

  def getXValues(): mutable.Buffer[Double] ={
    x_values
  }

  def getYValues(): mutable.Buffer[Double] ={
    y_values
  }
}
