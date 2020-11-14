package com.yangwj.spark.study.scala.akka.chatbot

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.io.StdIn

/**
 * @author yangwj
 * @date 2020/8/7 22:17
 * @version 1.0
 */
class AClienActor extends  Actor{

  var serverActorRef:ActorSelection = _

  override def preStart(): Unit = {
    context.actorSelection("akka.tcp://server@127.0.0.1:8888/user/serverPort")
  }

  override def receive: Receive = {
    case "start" => println("A客户端已启动....")
    case msg:String => {
      println(s"${msg}")
      serverActorRef ! ClientMessage(msg)
    }
    case ServerMessage(msg) => println(s"收到服务端消息:${msg}")
  }
}

object AClienActor extends App {
  val host:String = "127.0.0.1"
  val port:String = "8889"
  private val config: Config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = $host
       |akka.remote.netty.tcp.port = $port
      """.stripMargin
  )
  private val actorSystem: ActorSystem = ActorSystem("client", config)
  private val aRef: ActorRef = actorSystem.actorOf(Props[AClienActor], "A")
  aRef ! "start"

  while (true){
    val aClientQuestion: String = StdIn.readLine()
    aRef ! aClientQuestion
  }
}
