package com.yangwj.spark.study.scala.defineclass.v1

/**
 * @author yangwj
 * @date 2020/8/5 22:49
 * @version 1.0
 */
class Student{}
object Person {

  def main(args: Array[String]): Unit = {
    //在scala中可以动态的混入N个特质
    val student = new Student with ScalaTrait with Fly
    student.sayHello("你好呀")
    student.fly("wo")
  }
}
