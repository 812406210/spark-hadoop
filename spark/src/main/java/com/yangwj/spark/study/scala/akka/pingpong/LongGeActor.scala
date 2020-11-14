package com.yangwj.spark.study.scala.akka.pingpong

import akka.actor.{Actor, ActorRef}

/**
 * @author yangwj
 * @date 2020/8/7 21:24
 * @version 1.0
 */
class LongGeActor(val fg:ActorRef)  extends Actor{
  //接受消息
  override def receive: Receive = {
    case "start" => {
      println("龙哥准备好了")
      fg ! "啪"
    }
    case "啪啪" =>{
      println("峰哥真猛")
      Thread.sleep(1000)
      fg! "啪"
    }
  }
}
