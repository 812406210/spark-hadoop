package com.yangwj.spark.study.scala.sparkdemo

/**
 * @author yangwj
 * @date 2020/8/8 9:17
 * @version 1.0
 */

//woker向master注册信息
case class RegisterWorkerInfo(id:String,core:Int,ram:Int)

//master发送信息给worker，表示worker已注册成功
case object RegisteredWorkerInfo

//存储woker信息类
class  WorkerInfo(var id:String,core:Int,ram:Int){
  var lastHeartBeatTime:Long = _
}

//worker向master发送心跳信息
case class HeartBeat(id:String)

// worker 发送发送给自己的消息，告诉自己说要开始周期性的向master发送心跳消息
case object SendHeartBeat

//master自己给自己发送一个检查超时master的信息
case object  CheckTimeOutWorker

case object  RemoveCheckTimeOutWorker
