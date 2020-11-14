package com.yangwj.spark.study.scala.defineclass

/**
 * @author yangwj
 * @date 2020/8/4 22:00
 * @version 1.0
 */
object ScalaStatic {

  /**
   * object 是一个单例对象，无法new一个对象
   * object中的成员变量和方法都是静态的
   * 都可以通过类名.调用
   */
  val name:String = "scala class"

  val age:Int = 20

  def saySomething(str:String):Unit={
    println(s"str=${str}")
  }

  /**
   * 可以ScalaStatic("青菜")调用
   * @param food
   */
  def apply(food:String) = {
    println(s"来，干一杯 吃点${food}")
  }


}
