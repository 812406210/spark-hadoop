package com.yangwj.spark.study.scala

/**
 * @author yangwj
 * @date 2020/8/6 22:32
 * @version 1.0
 */

/**
 * 样例类:case支持模式匹配，可以封装数据，默认实现了Serializable接口
 */
case class Message(sender:String,content:String)

/**
 * 样例对象：用于模式匹配，不能封装数据
 */
case object Sender

object CaseDemo {
  def main(args: Array[String]): Unit = {
    val message = new Message("11", "2")
    val content: String = message.content
    println(s"content=${content}")
  }
}
