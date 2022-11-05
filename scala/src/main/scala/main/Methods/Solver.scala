package main.Methods

import scala.collection.mutable

object Solver {
  private def crate_basic_polynomial(x_values: mutable.Buffer[Double], i: Int): Double => Double = {
    def basic_polynomial(x: Double): Double = {
      var divider: Double = 1.0
      var result: Double = 1.0
      for(j <- x_values.indices){
        if(j != i){
          result *= (x-x_values(j))
          divider *= (x_values(i) - x_values(j))
        }
      }
      result/divider
    }
    basic_polynomial
  }

  def create_Lagrange_polynomial(x_values: mutable.Buffer[Double], y_values: mutable.Buffer[Double]): Double => Double = {
    val basic_polynomials = mutable.Buffer[Double => Double]()
    for(i <- 0 to x_values.size){
      basic_polynomials += crate_basic_polynomial(x_values, i)
    }

    def lagrange_polynomial(x: Double):Double ={
      var result: Double = 0.0
      for (i <- y_values.indices){
        result += y_values(i) * basic_polynomials(i).apply(x)
      }
      result
    }
    lagrange_polynomial
  }


  private def divided_differences(x_values: mutable.Buffer[Double], y_values: mutable.Buffer[Double], k: Int): Double = {
    var result: Double = 0.0
    for(j <- 0 to k){
      var mul: Double = 1.0
      for (i <-0 to k) {
        if (i != j) {
          mul *= (x_values(j) - x_values(i))
        }
      }
      result += y_values(j) / mul
    }
    result
  }

  def create_Newton_polynomial(x_values: mutable.Buffer[Double], y_values: mutable.Buffer[Double]): Double => Double = {
    val div_diff = mutable.Buffer[Double]()
    for(i <- 1 until x_values.size){
      div_diff += divided_differences(x_values, y_values, i)
    }

    def newton_polynomial(x: Double): Double = {
      var result: Double = y_values.head
      for(k <- 1 until y_values.size){
        var mul: Double = 1.0
        for(j <- 0 until k) {
          mul *= (x-x_values(j))
        }
        result += div_diff(k-1) * mul
      }
      result
    }
    newton_polynomial
  }

  def create_finite(y_values: mutable.Buffer[Double], k: Int): Double ={
    y_values(k)-y_values(k-1)
  }

  var defs = mutable.Buffer[mutable.Buffer[Double]]()

  def create_Newton_polynomial_finite(x_values: mutable.Buffer[Double], y_values: mutable.Buffer[Double]): Double => Double = {
    defs += y_values
    for(i <- 1 until y_values.size){
      defs += mutable.Buffer[Double]()
      for(j <- 1 until defs(i-1).size - 1){
        defs.last += create_finite(defs(defs.size - 2), j)
      }
//      print(f"${defs(i).last}%.2f ")
    }
    defs.foreach(_.foreach(a => print(f"${a}%.5f ")))
    print('\n');
    x => x
  }
}
