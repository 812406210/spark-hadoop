package com.yangwj.spark.study.scala.akka.chatbot

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

/**
 * @author yangwj
 * @date 2020/8/7 22:07
 * @version 1.0
 */
class ServerActor extends Actor{
  //用来接收客户端发送过来的问题
  override def receive: Receive = {
    case "start" => println("server启动了......")
    case ClientMessage(msg) =>{
        println(s"收到客户端消息:${msg}")
         msg match {
           case "你叫啥" => sender() ! ServerMessage("阿铁啊...")
           case "你是男是女" => sender() ! ServerMessage("哥们是男的啊")
           case "你有女票吗" => sender() ! ServerMessage("没有")
           case _ => sender() ! ServerMessage("what are u saying?")
         }
    }

  }
}

object ServerActor extends App {
  val host:String = "127.0.0.1"
  val port:String = "8888"
  private val config: Config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.hostname = $host
       |akka.remote.netty.tcp.port = $port
      """.stripMargin
  )
  //指定IP和端口
  private val actorSystem: ActorSystem = ActorSystem("server", config)
  private val serverRef: ActorRef = actorSystem.actorOf(Props[ServerActor],"serverPort")
  serverRef ! "start"
}
