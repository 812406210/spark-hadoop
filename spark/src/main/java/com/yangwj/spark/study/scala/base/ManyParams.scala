package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/2 9:51
 * @version 1.0
 */
object ManyParams {

  /**
   * 可变参数
   * @param ints
   * @return
   */
  def add(ints:Int*):Int ={
    var sum = 0
    for (a <- ints){
      sum +=a
    }
    sum
  }

  /**
   * 可变参数，一般是放在参数最后
   * @param initValue
   * @param ints
   * @return
   */
  def add1(initValue:Int,ints:Int*):Int ={
    var sum = initValue
    for (a <- ints){
      sum +=a
    }
    sum
  }

  /**
   * 给方法一个默认值
   * @param initValue
   * @param a
   * @return
   */
  def add2(initValue:Int = 2,a:Int = 3):Int ={
    (initValue+a)
  }


  def add4(a:Int,b:Int):Int = {
    a+b
  }

  /**
   * 柯里化函数
   * @param a
   * @param b
   * @return
   */
  def add5(a:Int)(b:Int):Int= {
    a+b
  }

  def func(str:String):Int = {
    if(str.equals("a")) 97 else  0
  }

  /**
   * 偏函数   (模式匹配)
   * 偏函数定义：PartialFunction[参数类型，返回值类型]
   * @return
   */
  def func1:PartialFunction[Any,Int] = {
    case "a" =>97  //值匹配
    case i:Int => i*10  //参数类型匹配
    case _ => 0
  }



  def main(args: Array[String]): Unit = {

    //可变传参
    println(add(6))
    println(add(1,2))
    println(add1(1,2,3,4,5,6,7,8,9))

    //部分参数应用函数
    var partAdd = add4(1,_:Int)
    val partResult: Int = partAdd(14)
    println(s"partResult=${partResult}")


    //柯里化
    val intToInt: Int => Int = add5(2)
    val result: Int = intToInt(5)
    println(s"柯里化结果result=${result}")
    println(s"柯里化结果result1=${add5(2)(5)}")

    //偏函数
    println(s"偏函数=${func1("a")}")

    println(s"偏函数=${Array("a",2,3).collect(func1).toBuffer}")
  }

}
