package com.yangwj.spark.study.scala.sparkdemo

import java.util.UUID

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration._

/**
 * @author yangwj
 * @date 2020/8/8 9:03
 * @version 1.0
 */
class Worker(masterUrl:String)  extends Actor{

  var masterProxy:ActorSelection = _
  val workerId = UUID.randomUUID().toString

  override def preStart(): Unit = {
    masterProxy = context.actorSelection(masterUrl)
  }

  override def receive: Receive = {
    //worker要向master注册自己的信息
    case "start" => {
      println(s"自己已准备好了....")
      //此时，master会收到work发送的消息
      masterProxy ! RegisterWorkerInfo(workerId,4,32*1024)
    }
    case RegisteredWorkerInfo =>{
      //表示自己发送给master注册信息成功了
      //worker启动一个定时器，定时向master发送心跳
      import context.dispatcher
      context.system.scheduler.schedule(0 millis,1500 millis,self,SendHeartBeat)
    }
    case SendHeartBeat =>{
      //开始向master发送心跳
      masterProxy ! HeartBeat(workerId)
    }

  }
}

object Worker {

  def main(args: Array[String]): Unit = {

    //参数校验
    if(args.length !=3){
       println(
         """
           |请输入正确参数: <host> <port> <workName> <masterUrl>
           |""".stripMargin)
    }

    val host= args(0)
    val port = args(1)
    val masterUrl = args(2)
    val workName = args(3)
    val config: Config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = $host
         |akka.remote.netty.tcp.port = $port
      """.stripMargin
    )
    val worker: ActorSystem = ActorSystem("worker",config)

    //创建自己的actorRef
    val workerRef: ActorRef = worker.actorOf(Props(new Worker(masterUrl)), name =workName)
    workerRef ! "started"
  }
}
