package com.yangwj.spark.study.sparkstreaming.v2

import java.sql.{Connection, DriverManager, ResultSet, Statement}
import java.text.SimpleDateFormat
import java.util.Date


/**
  * @author yangwj
  * @date 2020/8/5 10:25
  */
class MysqlConnectPool {

  private var connection: Connection = _
  //mysql8配置
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val url = "jdbc:mysql://localhost:3306/spark?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=CTT"
  private val username = "root"
  private val password = "yang156122"
//mysql5，记得pom的驱动也得是mysql5
//  private val driver = "com.mysql.jdbc.Driver"
//  private val url = "jdbc:mysql://localhost:3306/spark?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=true"
//  private val username = "root"
//  private val password = "yang156122"

  /*** 创建mysql连接 ** @return*/
  def conn(): Connection = {

    if (connection == null) {
      println(this.driver)
      Class.forName(this.driver)
      connection = DriverManager.getConnection(this.url, this.username, this.password)
    }
    connection
  }

  def close(conn: Connection,stat: Statement): Unit = {
    try {
      if(!stat.isClosed() || stat!=null){
        stat.close()
      }
      if (!conn.isClosed() || conn != null) {
        conn.close()
      }

    } catch {
        case ex: Exception => {
              ex.printStackTrace()
      }
    }
  }

  def currentTime():String = {
    val date = new Date()
    val time: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    time
  }

  //添加
  def insert(stat: Statement,field:String,times:Int) = {
//    val conn: Connection = MysqlConnectPool.conn()
//    val stat: Statement = conn.createStatement()
    try {
      val result: Int = stat.executeUpdate(s"INSERT INTO `spark_test`( `create_time`,`field`, `times`) VALUES ( '${currentTime()}','${field}', ${times})")
      println(s"添加数据，返回值=>" + result)
    }finally {
      //MysqlConnectPool.close(conn,stat)
    }
  }

  //删除
  def delete(stat: Statement,field:String) = {
//    val conn: Connection = MysqlConnectPool.conn()
//    val stat: Statement = conn.createStatement()
    try {
      val result: Int = stat.executeUpdate(s"DELETE FROM `spark_test` WHERE `field` = '${field}'")
      println(s"删除数据，返回值=>" + result)
    }finally {
     // MysqlConnectPool.close(conn,stat)
    }

  }

  //查询
  def selectByField(stat: Statement,field:String):ResultSet = {
//    val conn: Connection = MysqlConnectPool.conn()
//    val stat: Statement = conn.createStatement()
    try {
      val result: ResultSet = stat.executeQuery(s"select * FROM `spark_test` WHERE `field` = '${field}'")
      println(s"查询数据，返回值=>" + result.wasNull())
      result
    }finally {
     // MysqlConnectPool.close(conn,stat)
    }
  }

  def update(stat: Statement,field:String,times:Int): Unit = {
//    val conn: Connection = MysqlConnectPool.conn()
//    val stat: Statement = conn.createStatement()
    try {
      val resUpdate = stat.executeUpdate(s"UPDATE `spark_test` SET `times` = '${times}'  WHERE `field` = '${field}'")
      println(s"更新数据，返回值=>" + resUpdate)
    }finally {
     // MysqlConnectPool.close(conn,stat)
    }
  }

}
