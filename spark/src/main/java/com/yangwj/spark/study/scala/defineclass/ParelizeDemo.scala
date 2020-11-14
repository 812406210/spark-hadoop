package com.yangwj.spark.study.scala.defineclass

/**
 * @author yangwj
 * @date 2020/8/5 22:24
 * @version 1.0
 */
object ParelizeDemo extends App {
  var list = List(1,2,3,4)
  private val sum: Int = list.par.fold(100)(_ + _) //无方向
  println(s"sum=${sum}")

  private val sum1: Int = list.par.foldLeft(100)(_ + _) //有方向
  println(s"sum1=${sum1}")

  private val aggregate: Int = list.par.aggregate(100)(_+_,_+_) //无方向
  println(s"aggregate=${aggregate}")


}
