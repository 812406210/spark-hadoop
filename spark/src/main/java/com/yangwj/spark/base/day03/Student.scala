package com.yangwj.spark.base.day03

trait StudentTrait {

    type T

    def learn(s: T) = {
        println(s)
    }

}
