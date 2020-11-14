package com.yangwj.spark.study.scala.akka.pingpong

import akka.actor.{ActorRef, ActorSystem, Props}


/**
 * @author yangwj
 * @date 2020/8/7 21:26
 * @version 1.0
 */
object PingPongApp extends App {

  //工厂
  private val pingPongActorSystem: ActorSystem = ActorSystem("PingPongActorSystem")

  //峰哥引用
  private val fgRef: ActorRef = pingPongActorSystem.actorOf(Props[FengGeActor], name = "fg")
  //龙哥引用
  private val lgRef: ActorRef = pingPongActorSystem.actorOf(Props(new LongGeActor(fgRef)), "lg")

  lgRef ! "start"
  fgRef ! "start"

}
