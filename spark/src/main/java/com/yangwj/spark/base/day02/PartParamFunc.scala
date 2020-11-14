package com.yangwj.spark.base.day02

import java.util.Date

object PartParamFunc extends App{

    // 定义个输出的方法, 参数为date, message
    def log(date: Date, message: String) = {
        println(s"$date, $message")
    }

    val date = new Date()

    // 调用log的时候, 传递了一个具体的时间参数, message 为待定参数
    // logBoundDate 成了一个新的函数, 只有log的部分参数(message)
    val logBoundDate = log(date, _: String)

    // 调用logBoundDate的时候, 只需要传递待传的message参数即可
    //logBoundDate("fuck jerry ")
    // logBoundDate("fuck 涛涛 ")

    log(date, "fuck jerry ")
    log(date, "fuck 涛涛 ")

}
