package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/2 18:21
 * @version 1.0
 */
object ArrayDemo {

  def main(args: Array[String]): Unit = {
      var arr = Array(1,2,3,4)
    val ints: Array[Int] = arr.filter(x => x != 2)
    println(ints.toBuffer)
  }

}
