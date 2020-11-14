package com.yangwj.spark.base.day02

object DefaultParams {

    /**
      * 参数定义时可以给定一个默认值
      */
    def add(a: Int = 6, b: Int = 5) = {
        a+b
    }

    def main(args: Array[String]): Unit = {
        println(add()) // 调用时，如果不传递参数，即会使用函数或者方法的默认值
        println(add(7, 8)) // 调用时，如果传递了参数值，则使用传递的参数值
        println(add(9)) // 调用时，9覆盖了a=6
        println(add(b=9)) //
        println(add(b=9, a=9))
        // println(add(c=9, a=9)) 覆盖参数时，参数的名称必须和方法定义的名称保持一致
    }

}
