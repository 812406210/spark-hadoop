package com.yangwj.spark.base.day03

class Student {

}

object Person {

    def main(args: Array[String]): Unit = {

        /**
          * 在scala中可以动态的混入N个特质
          */
        val student = new Student with Fly with ScalaTrait
        //student.hello("老羊")
        student.fly("gaoxing")
        student.small("丁丁")


    }

}
