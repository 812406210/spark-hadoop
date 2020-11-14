package com.yangwj.spark.base.day03

class AnimalImpl(val name: String) {

}

/**
  * 编译器在编译会从右往左进行编译检查
  */
//object Pig extends Dog with Animal{
//
//
//    override def run(): Unit = {
//        println("跑.....")
//    }
//
//    def main(args: Array[String]): Unit = {
//        // Pig.run()
//
//        new AnimalImpl("Cat")
//    }
//}


object TestAnimal{

    def main(args: Array[String]): Unit = {
        val cat = new AnimalImpl("Cat") with Dog {

            override val name = "fox"

            override def run(): Unit = {
                println("----")
            }
        }
        println(cat.name)
    }
}

