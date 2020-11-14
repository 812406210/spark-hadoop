package com.yangwj.spark.study.scala.akka.chatbot

/**
 * @author yangwj
 * @date 2020/8/7 22:28
 * @version 1.0
 */
//服务端发送给客户端的消息格式
case class ServerMessage(msg:String)
//客户端发送给服务端的消息格式
case class ClientMessage(msg:String)
