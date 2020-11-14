package com.yangwj.spark.study.scala.implicitDemo

/**
 * @author yangwj
 * @date 2020/8/8 10:31
 * @version 1.0
 */
object ScalaImplicit {

  //隐式参数, 注意：方法参数有多个，隐式参数必须放在最后
  def say(implicit content:String) = {println(s"${content}")}

  //隐式方法
  implicit  def double2Int = (d:Double) => d.toInt
  def main(args: Array[String]): Unit = {
      //调用了隐式参数
      implicit val msg  = "哈哈...."
      say
    //调用了隐式函数
    val d:Int = 3.14
    println(d)
  }

}
