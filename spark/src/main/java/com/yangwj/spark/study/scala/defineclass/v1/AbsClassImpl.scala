package com.yangwj.spark.study.scala.defineclass.v1

/**
 * @author yangwj
 * @date 2020/8/5 22:59
 * @version 1.0
 */
object AbsClassImpl extends AbstractClass {
  def main(args: Array[String]): Unit = {
    val result: String = AbsClassImpl.eat("wo")
    println(s"${result}")
  }
}
