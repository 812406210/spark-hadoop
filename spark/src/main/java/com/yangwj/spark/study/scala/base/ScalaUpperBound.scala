package com.yangwj.spark.study.scala.base

/**
 * @author yangwj
 * @date 2020/8/8 21:20
 * @version 1.0
 */
//上界，不会发生隐式转换
class CommCmp[T <: Comparable[T]](o1:T,o2:T){
  def bigger = if(o1.compareTo(o2)>0) o1 else o2
}
//视图界定，会发生隐式转换  Comparable
class CommCmpView[T <% Comparable[T]](o1:T,o2:T){
  def bigger = if(o1.compareTo(o2)>0) o1 else o2
}

//视图界定，会发生隐式转换  Ordered
class CommCmpViewOrder[T <% Ordered[T]](o1:T,o2:T){
  def bigger = if (o1 > o2) o1 else o2
}

class TeacherCmp(var name:String,var age:Int) extends Ordered[TeacherCmp]{

  override def toString = s"TeacherCmp($name, $age)"

  override def compare(that: TeacherCmp): Int = this.age - that.age
}
object ScalaUpperBound {
  def main(args: Array[String]): Unit = {

    val value = new CommCmp(Integer.valueOf(8), Integer.valueOf(5))
    println(s"上界:${value.bigger}")

    val valueView = new CommCmpView(9,10)
    println(s"上界:${valueView.bigger}")

    val yang = new TeacherCmp("yang", 73)
    val wen = new TeacherCmp("wen", 40)

    val valueViewTeacher = new CommCmpView(yang,wen)
    println(s"上界:${valueViewTeacher.bigger}")

  }

}
