package com.yangwj.spark.sparkml

import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
/**
  * @author yangwj
  * @date 2020/7/28 14:52
  */
object SVM extends SharedSparkContext {
  def main(args: Array[String]): Unit = {

    // Load training data in LIBSVM format.
    val data = MLUtils.loadLibSVMFile(spark.sparkContext, "D:\\mydata\\ml\\dt\\sample_libsvm_data.txt")

    // Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    // Run training algorithm to build the model
    val model = new LogisticRegressionWithLBFGS()
      .setNumClasses(10)
      .run(training)

    // Compute raw scores on the test set.
    val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
      val prediction = model.predict(features)
      (prediction, label)
    }

    // Get evaluation metrics.
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val accuracy = metrics.accuracy
    println(s"Accuracy = $accuracy")

    // Save and load model
   model.save(spark.sparkContext, "D:\\mydata\\ml\\dt\\decideTree\\tmp\\scalaLogisticRegressionWithLBFGSModel")
   val sameModel = LogisticRegressionModel.load(spark.sparkContext,
     "D:\\mydata\\ml\\dt\\decideTree\\tmp\\scalaLogisticRegressionWithLBFGSModel")
//    if(sameModel == null){
//      model.save(spark.sparkContext, "D:\\mydata\\ml\\dt\\decideTree\\tmp\\scalaLogisticRegressionWithLBFGSModel")
//    }
//    //----------加载模型进行测试
//    val predictionAndLabelsTest = test.map { case LabeledPoint(label, features) =>
//      val prediction = sameModel.predict(features)
//      (prediction, label)
//    }
//    val metricsTest = new MulticlassMetrics(predictionAndLabelsTest)
//    val accuracyTest = metricsTest.accuracy
//    println(s"accuracyTest = $accuracyTest")

  }

}
