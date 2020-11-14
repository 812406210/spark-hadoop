package com.yangwj.spark.study.scala.defineclass.v1

/**
 * @author yangwj
 * @date 2020/8/5 22:38
 * @version 1.0
 */
object ScalaTraitImpl extends  ScalaTrait with App {

  //实现父类中的方法
  override def sayHello(name: String): Unit = {
    println(s"hello=${name}")
  }

  ScalaTraitImpl.sayHello("world")
  ScalaTraitImpl.smile("真好")

  //重写
  override def smile(name: String): Unit = {
    println(s"学校${name}")
  }
}
