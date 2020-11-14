package com.yangwj.spark.study.scala.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
 * @author yangwj
 * @date 2020/8/7 21:03
 * @version 1.0
 */
//继承actor,创建actor
class HelloActor extends Actor {
  //用来接受消息
  override def receive: Receive = {
    case "你好帅" => println(s"尽说实话")
    case "stop" => {
      //上下文
      context.stop(self) //关闭actor
      context.system.terminate() //关闭actor工厂
    }
    case _ => println(s"瞎说")
  }
}

object HelloActor {
  //actor工厂
  private val factory: ActorSystem = ActorSystem("Factory")
  //返回HelloActor引用
  private val helloActorRef: ActorRef = factory.actorOf(Props[HelloActor], "helloActor")

  def main(args: Array[String]): Unit = {
    //给自己发送消息(发送消息使用"!")
    helloActorRef ! "你好帅"
    helloActorRef ! "哈哈"
    helloActorRef ! "stop"
  }
}
