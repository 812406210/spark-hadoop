package com.yangwj.spark.base.day02

object ManyParams {

    /**
      * 可变参数， 在参数类型后面加上一个通配符 *
      */
    def add(ints: Int*): Int = {
        var sum = 0
        for (v <- ints) {
            sum += v
        }
        sum
    }

    /**
      * 可变参数一般放在参数列表的末尾
      */
    def add1(initValue: Int, ints: Int*): Int = {
        var sum = initValue
        for (v <- ints) {
            sum += v
        }
        sum
    }

    /**
      * Any 任意类型
      */
    def makePerson(params: Any*): Unit = {

    }

    def main(args: Array[String]): Unit = {

        println(add(6))
        println(add(1,2))
        println(add(1,2,3,4,5,6,7,8,9))

    }

}
