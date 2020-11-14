package com.yangwj.spark.base.day03

/**
  * 支持模式匹配，默认实现了Serializable接口
  *
  * 样例类： case class 类名(属性....)
  * 类名的定义必须是驼峰式，属性名称第一个字母小写
  */
case class Message(sender: String, messageContent: String)

/**
  * 默认实现了Serializable接口
  * 样例对象：case object 对象名
  * 模式匹配
  * 样例对象不能疯转数据
  */
case object CheckHeartBeat


object TestCaseClass{

    def main(args: Array[String]): Unit = {
        val message = new Message("刘亦菲", "今天晚上吃饭")

    }
}