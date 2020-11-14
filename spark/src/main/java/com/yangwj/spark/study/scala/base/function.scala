package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/1 22:20
 * @version 1.0
 */
object function {

  def main(args: Array[String]): Unit = {
    var sum = add(1,2)
    println(s"a+b=${sum}")

    println(add _) //方法转为函数
    val mutlValue = mutl(2,3)
    println(s"a*b=${mutlValue}")

  }

  //方法定义：def 方法名(参数:类型,....):返回值类型 = {函数体}
  def add(a:Int,b:Int):Int = {
    a+b
  }

  //函数定义：val 函数名(参数类型,....)=>返回值类型 = (参数引用,....) =>{函数体}
  //        val 函数名=(参数:参数类型,....)=>{函数体}
  val mutl:(Int,Int) => Int =(a,b) =>{
    a*b
  }

}
