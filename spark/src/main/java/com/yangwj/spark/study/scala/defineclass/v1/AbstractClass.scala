package com.yangwj.spark.study.scala.defineclass.v1

/**
 * @author yangwj
 * @date 2020/8/5 22:54
 * @version 1.0
 * @desc 使用    abstract定义抽象类，可以定义方法，也可以定义实现的方法
 */
abstract class AbstractClass {

  def eat(name:String):String = {
    name+"正在吃......"
  }

}
