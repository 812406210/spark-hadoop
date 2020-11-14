package com.yangwj.spark.base.day03

object ParallelSeq {

    def main(args: Array[String]): Unit = {

        val list = List(1,2,3,4,5)
        list.par.fold(0)(_ + _)

        list.par.foldLeft(0)(_ + _)

        // list.par.aggregate()


    }

}
