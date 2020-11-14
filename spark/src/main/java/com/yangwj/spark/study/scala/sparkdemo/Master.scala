package com.yangwj.spark.study.scala.sparkdemo

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import scala.concurrent.duration._
/**
 * @author yangwj
 * @date 2020/8/8 9:03
 * @version 1.0
 */
class Master  extends Actor{
  //存储woker信息
  var id2WorkerInfo = collection.mutable.HashMap[String,WorkerInfo]()

  override def receive: Receive = {

    //收到work注册过来的消息
    case RegisterWorkerInfo(workerId,core,ram) =>{
      //将worker的信息存储起来，存储到hashMap
      val workerInfo = new WorkerInfo(workerId, core, ram)
      if(!id2WorkerInfo.contains(workerId)) {
        id2WorkerInfo += ((workerId, workerInfo))
      }
      //master存储完worker数据，要告诉woker说你已经发送成功
      sender() ! RegisteredWorkerInfo
    }

    case HeartBeat(workerId) =>{
      //master收到worker的心跳消息之后，更新woker的上一次心跳时间
      val workerInfo: WorkerInfo = id2WorkerInfo(workerId)
      workerInfo.lastHeartBeatTime = System.currentTimeMillis()
    }

    case CheckTimeOutWorker =>{
      import context.dispatcher
      context.system.scheduler.schedule(0 millis,6000 millis,self,RemoveCheckTimeOutWorker)
    }

    case RemoveCheckTimeOutWorker =>{
      //将hashmap中所有的value都拿出来，查看当前时间和上一次心跳时间的差 3000
      val values: Iterable[WorkerInfo] = id2WorkerInfo.values
      val currentTime = System.currentTimeMillis()
      //过滤超时的信息
      values.filter(workInfo => currentTime - workInfo.lastHeartBeatTime >3000)
        .foreach(wk => id2WorkerInfo.remove(wk.id))

      println(s"-------------------还剩${id2WorkerInfo.size}存活的worker----------")
    }
  }
}

object Master {

  def main(args: Array[String]): Unit = {

    //参数校验
    if(args.length !=3){
      println(
        """
          |请输入正确参数: <host> <port> <masterName>
          |""".stripMargin)
    }

    val host= args(0)
    val port = args(1)
    val masterName = args(2)
    val config: Config = ConfigFactory.parseString(
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = $host
         |akka.remote.netty.tcp.port = $port
      """.stripMargin
    )
    val master: ActorSystem = ActorSystem("master",config)

    //创建自己的actorRef
    val masterRef: ActorRef = master.actorOf(Props[Master], name = masterName)
    masterRef ! CheckTimeOutWorker
  }
}


