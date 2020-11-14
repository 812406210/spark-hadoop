package com.yangwj.spark.study.scala.iterable

/**
 * @author yangwj
 * @date 2020/8/4 20:15
 * @version 1.0
 */
object ListDemo {
  def main(args: Array[String]): Unit = {

    val list = List(3,5,1)
    // ((0-3) - 5) -1
    val result: Int = list.fold(1)(_ + _)
    println(s"result=${result}")

    // 1 - (5 - (3-0))
    val right: Int = list.foldRight(0)(_ - _)
    println(s"right=${right}")

    //(x,y)=>x+y 部分聚合    (a,b)=>a+b 全局聚合
    val aggregate: Int = list.aggregate(0)((x,y)=>x+y,(a,b)=>a+b)
    println(s"aggregate=${aggregate}")

    val list1 = List(3,5,1,4)

    val union: List[Int] = list.union(list1)
    println(s"union=${union}")

    val intersect: List[Int] = list.intersect(list1)
    println(s"intersect=${intersect}")

    val diff: List[Int] = list1.diff(list)
    println(s"diff=${diff}")

    val zip: List[(Int, Int)] = list1.zip(list)
    println(s"zip=${zip}")

    val slice: List[Int] = list.slice(1, list.length).map(_ * 10)
    println(s"slice=${slice}")

    val count: Int = list.count(x => x > 2)
    println(s"count=${count}")


  }

}
