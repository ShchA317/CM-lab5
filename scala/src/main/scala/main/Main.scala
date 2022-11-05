package main

import input.CsvReader
import plot.CsvWriter
import main.Methods.Solver

import scala.io.StdIn
import scala.collection.mutable
import scala.sys.process.*

object Main extends App{
  val functions: List[Function] = List[Function](
    new Function(x => scala.math.sin(x),"sin(x)"),
    new Function(x => scala.math.cos(x),"cos(x)"),
    new Function(x => x*x*x*x*x*x*x*x*x*x,"x^10"),
  )

  println("Привет, это пятая лаба по вычмату!")
  println("Сейчас будем интерполировать :)")
  println("Где будем брать точки?")
  println("1: В файле input/input1.csv")
  println("2: Зададим функцией")

  val choise = StdIn.readLine()
  val x_values = mutable.Buffer[Double]()
  val y_values = mutable.Buffer[Double]()

  if(choise == "1"){
    fromCsv()
  } else if (choise == "2"){
    println("которой? (есть только заданные, парсер прикручивать лень)")
    for(i <- functions.indices) {
      val name = functions(i).name
      println(s"${i+1}: \t $name")
    }

    val chosenFunction: Int = StdIn.readInt()
    if(chosenFunction > functions.size || chosenFunction < 1){
      println("Такой нет(")
      sys.exit()
    }

    println("Введите левый край промежутка в виде десятичной дроби")
    val chosenSegment = (StdIn.readDouble(), {println("Введите правый край промежутка в виде десятичной дроби"); StdIn.readDouble()})
    println("Сколько точек вы желаете видеть на промежутке?")
    val chosenParts = StdIn.readInt()
    val h: Double = (chosenSegment._2 - chosenSegment._1)/chosenParts
    var cur: Double = chosenSegment._1
    while(cur <= chosenSegment._2){
      x_values += cur
      y_values += functions(chosenFunction-1).f(cur)
      cur += h
    }
  } else {
    println("Упал(")
    sys.exit()
  }

  def fromCsv(): Unit ={
    x_values++= CsvReader.getXValues()
    y_values++= CsvReader.getYValues()
    println(s"x: ${x_values}")
    println(s"y: ${y_values}")
  }

  println("Для какой точки будем считать значение?")
  val chosenPoint = StdIn.readDouble()

  val lag_pol = Solver.create_Lagrange_polynomial(x_values, y_values)
  val new_pol = Solver.create_Newton_polynomial(x_values, y_values)
  val new_pol_f = Solver.create_Newton_polynomial_finite(x_values, y_values)
  
  val y_resList: mutable.Buffer[Double] = mutable.Buffer[Double]()
  val x_resList: mutable.Buffer[Double] = mutable.Buffer[Double]()
  CsvWriter.writeCsv(x_values, y_values, "input/input.csv")
  val y_point_L = lag_pol(chosenPoint)
  val y_point_N = new_pol(chosenPoint)
  println(s"Результат для выбранной точки: ${y_point_L} - через полином Лагранджа. ${y_point_N} - через полином Ньютона")

  CsvWriter.writeCsv(mutable.Buffer[Double](chosenPoint), mutable.Buffer[Double](y_point_L), "tables/for_point.csv")

  var i = x_values.head
  while(i < x_values.last){
    y_resList += lag_pol(i)
    x_resList += i
    i += 0.0001
  }

  CsvWriter.writeCsv(x_resList, y_resList, "tables/lagrange_polynomial.csv")
  x_resList.clear();  y_resList.clear()
  i = x_values.head
  while(i < x_values.last){
    y_resList += new_pol(i)
    x_resList += i
    i += 0.0001
  }
  
  CsvWriter.writeCsv(x_resList, y_resList, "tables/newton_polynomial.csv")
  "gnuplot src/main/scala/plot/gnuplot-script.gnu".!
}
