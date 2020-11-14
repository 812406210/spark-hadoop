package com.yangwj.spark.study.scala.defineclass

/**
 * @author yangwj
 * @date 2020/8/4 22:03
 * @version 1.0
 */
object ClassDemo {
  def main(args: Array[String]): Unit = {

    val age: Int = ScalaStatic.age
    println(s"age=${age}")

    ScalaStatic.saySomething("今天拉肚子啦")
    ScalaStatic("青菜")
  }

}
