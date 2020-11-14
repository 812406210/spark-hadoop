package com.yangwj.spark.study.sparkstreaming.v2

import java.sql.Statement

/**
 * @author yangwj
 * @date 2020/8/5 21:01
 * @version 1.0
 */
object test {
  def main(args: Array[String]): Unit = {
    val pool = new MysqlConnectPool
    val stat: Statement = pool.conn().createStatement()
    val result: Int = stat.executeUpdate(s"INSERT INTO `spark_test`( `create_time`,`field`, `times`) VALUES ( '${pool.currentTime()}','上海', 2)")
    println(s"result=${result}")
  }

}
