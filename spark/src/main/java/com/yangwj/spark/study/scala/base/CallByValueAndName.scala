package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/1 22:53
 * @version 1.0
 */
object CallByValueAndName {
  var money =100

  def huaQian():Int ={
    money = money - 5
    money
  }

  def shuQian() ={
    println(money)
  }

  //传递值
  def printByValue(x:Int)={
      for(b<- 0 to 3){
        println(s"printByValue每次还剩:${x}元")
      }
  }

  //没有参数，返回值为Int类型饿函数  ----传递引用(=>)
  def printByName(x: => Int)={
    for(b<- 0 to 3){
      println(s"printByName每次还剩:${x}元")
    }
  }

  def main(args: Array[String]): Unit = {
    printByValue(huaQian())
    printByName(huaQian())
  }

}
