package com.example

import util.control.Breaks._

object ControlApp {
  def main(args: Array[String]): Unit = {
    var n = 1
    breakable {
      while (n < 20) {
        n += 1
        if (n == 18) {
          break()
        }
      }
    }

    println("ok")
  }
}
