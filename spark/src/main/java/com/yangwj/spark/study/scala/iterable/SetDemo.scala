package com.yangwj.spark.study.scala.iterable

/**
 * @author yangwj
 * @date 2020/8/4 20:51
 * @version 1.0
 */
object SetDemo {

  def main(args: Array[String]): Unit = {
    //set 无序不重复数组
    val set = Set(1,2,3,4,1)

    val map = Map[String,Int]("a"->1,"b"->2)

    // getOrElse存在key,则取对应的值，不存在返回指定的默认值
    val mapValue: Int = map.getOrElse("a", 0)
    println(s"mapValue=${mapValue}")

    val value: Int = map.get("a").get
    println(s"value=${value}")

    val tp = (1,"a","5",7)
    //productIterator 遍历
    tp.productIterator.foreach(println)

  }

}
