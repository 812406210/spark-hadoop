package com.yangwj.spark.study.scala.defineclass.v1

/**
 * @author yangwj
 * @date 2020/8/5 20:35
 * @version 1.0
 */
class Teacher(var name:String,var age:Int) {
  /**
   * scala定义类，默认有一个空参构造器
   * 定义在类后面叫做主构造器(主构造器中成员属性没有var修饰，则不被被访问)
   * 定义在类中的是辅助构造器(可以多个)
   * 在class前面加private[this],表示这个类在当前包下可用 ,当前子包不可见
   * 在class前面加private[包名],表示这个类在当前包下可用 ,当前子包也可见
   */
//  var name:String = _ //初始值为null
//  var age:Int = _ //初始化为0
  var sex:String = _
  var province:String = _
  //辅助构造器
  def this(name:String,age:Int,sex:String)={
    this(name,age)
    this.sex = sex
  }

  def this(name:String,age:Int,sex:String,province:String)={
    this(name,age,sex)
    this.province = province
  }
}

/***
 * object Teacher类是class Teacher的伴身对象
 * 在伴身对象中可以访问类的私有成员变量
 */
object  Teacher{
  /**
   * object 类() 默认调用的 apply()方法，也称语法糖
   * 调用对象的属性和方法，默认会执行apply方法
   * @param name
   * @param age
   * @return
   */
  def apply(name: String, age: Int): Teacher = {
    new Teacher(name,age)
  }
}
