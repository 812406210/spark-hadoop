package com.yangwj.spark.study.scala.akka.pingpong

import akka.actor.Actor

/**
 * @author yangwj
 * @date 2020/8/7 21:25
 * @version 1.0
 */
class FengGeActor extends Actor{
  //接受消息
  override def receive: Receive = {
    case "start" => println(s"峰哥准备好了")
    case "啪" => {
      println(s"龙哥你也猛啊......")
      Thread.sleep(1000)
      sender()  ! "啪啪"
    }
  }
}
