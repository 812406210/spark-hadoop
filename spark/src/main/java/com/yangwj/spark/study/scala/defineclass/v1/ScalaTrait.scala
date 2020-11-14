package com.yangwj.spark.study.scala.defineclass.v1

/**
 * 特质:相当于java中的interface
 */
trait ScalaTrait {
  def sayHello(name:String)={
    println(s"${name}")
  }

  def smile(name:String) = {
    println(s"校园${name}")
  }
}
