package com.yangwj.spark.sparkml

import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author yangwj
  * @date 2020/7/29 10:44
  */
object LogisticRegressionWithLBFGSExample {

  def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setMaster("local[*]").setAppName("LogisticRegressionWithLBFGSExample")
      val sc = new SparkContext(conf)
      //读取数据
      val data = MLUtils.loadLibSVMFile(sc,"D:\\mydata\\ml\\dt\\sample_libsvm_data.txt")
      val splits = data.randomSplit(Array(6.0,4.0),11L)
      //准备训练数据和测试数据
      val training = splits(0)
      val test = splits(1)

      //模型训练
      val model = new LogisticRegressionWithLBFGS().run(training)
      //清楚阈值
      //model.clearThreshold();

    //将数据映射位一个类
    val predictAndLables = test.map { case LabeledPoint(label, features) =>
      //测试集测试模型
      val predict = model.predict(features)
      //返回预测结果和真实标签数组
      (predict, label)
    }

    val metrics = new MulticlassMetrics(predictAndLables)
    val accuracy = metrics.accuracy
    println(s"accuracy is = $accuracy")

  }
}
