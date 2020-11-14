package com.yangwj.spark.study.sparkstreaming.v2

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author yangwj
 * @date 2020/7/16 21:03
 * @version 1.0
 */
object KafkaWordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[*]")

    val ssc = new StreamingContext(conf, Seconds(5))

    //kafka参数
    val zkQuorum = ""
    val grooupId = ""
    val topic = Map[String,Int]("TEST" ->1)
    //创建DStream,需要KafkaDStream
    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(ssc, zkQuorum, grooupId, topic)
    //kafka的ReceiverInputDStream[(String, String)]里面装的是一个元组(key是写入的key，即kafka分区key,value是数据部分)
    val lines: DStream[String] = data.map(_._2)

    val words: DStream[String] = lines.flatMap(_.split(" "))

    val wordAndOne: DStream[(String, Int)] = words.map((_, 1))
    val result: DStream[(String, Int)] = wordAndOne.reduceByKey(_ + _)

    result.print()
    ssc.start()

    ssc.awaitTermination()
  }

}
