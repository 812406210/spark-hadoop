package com.yangwj.spark.base.day02下午

object WordCount {

    def main(args: Array[String]): Unit = {

        val words: Array[String] = Array("hello tom hello jim", "hello hatano hello 菲菲")


        // words 数组中的每个元素进行切分

        //  Array(Array(hello,tom,hello,jim), Array(hello,hatano,hello,菲菲))
        val wordSplit: Array[Array[String]] = words.map((x: String) => x.split(" "))

        // 将数组中的Array扁平化
        // Array(hello,tom,hello,jim, hello,hatano,hello,菲菲)
        val fltWords: Array[String] = wordSplit.flatten

        // hello -> Array(hello, hello, hello, hello)
        val mapWords: Map[String, Array[String]] = fltWords.groupBy((wd: String) => wd)

        // (hello, 4), (tom, 1)。。。。
        val wrdResult: Map[String, Int] = mapWords.map(wdKV => (wdKV._1, wdKV._2.length))

        // Map不支持排序，需要将map转换成List， 调用sortBy方法按照单词数量降序排序
        val sortResult: List[(String, Int)] = wrdResult.toList.sortBy(t => - t._2)

        sortResult.foreach(t => println(t))

    }

}
