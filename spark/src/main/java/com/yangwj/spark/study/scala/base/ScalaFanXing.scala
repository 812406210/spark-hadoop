package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/8 14:57
 * @version 1.0
 */
//泛型:类型约束
abstract class Message[T](content:T)

class  StrMessage(content:String) extends Message(content )
object ScalaFanXing {



}
