package com.yangwj.spark.base.day02

object ArrayOpt {

    def main(args: Array[String]): Unit = {

        val arr = Array[Int](1,3,5,8,9)

        // map 映射
        val fx = (x: Int) => x * 10
        // arr 见过map映射操作之后会返回一个新的数组
        val r1 = arr.map(fx)

        arr.map((x: Int) => x * 10)
        arr.map(x => x * 10)
        arr.map(_ * 10)

        // flatten 扁平化操作
        val arr1: Array[String] = Array("hello hello tom", "hello jerry")

        // Array(Array("hello","hello","tom"), Array("hello", "jerry"))
        val r2: Array[Array[String]] = arr1.map(_.split(" "))

        // Array("hello","hello","tom", "hello", "jerry") 扁平化操作
        r2.flatten

        // flatMap = map -> flatten
        arr1.flatMap(_.split(" "))

        // arr1.flatMap(_.split(" ")).foreach(println)



        // 求每个单词出现的数量 word count

        //Array("hello","hello","tom", "hello", "jerry")
        val r3 = arr1.flatMap(x =>x.split(" "))

          // Map("hello"  -> Array("hello", hello, hello), tom -> Array("tom")
          .groupBy(x => x)

          //.map(x => x._2.length)
          .mapValues(x => x.length).toList.sortBy(x => - x._2)

        println(r3)
    }

}
