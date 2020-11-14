package com.yangwj.spark.sparkml

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.{BinaryClassificationMetrics, MulticlassMetrics}
import org.apache.spark.mllib.util.MLUtils
// $example off$
/**
  * @author yangwj
  * @date 2020/7/28 15:59
  */
object SVMWithSGDExeample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SVMWithSGDExeample").setMaster("local[*]")
    val sc = new SparkContext(conf)

    // $example on$
    // Load training data in LIBSVM format.
    val data = MLUtils.loadLibSVMFile(sc, "D:\\mydata\\ml\\dt\\sample_libsvm_data.txt")

    // Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)



    // Run training algorithm to build the model
    val numIterations = 100
    val model = SVMWithSGD.train(training, numIterations)

    // Clear the default threshold.
    model.clearThreshold()


    // Compute raw scores on the test set.
    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

    // Get evaluation metrics.
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
    val auROC = metrics.areaUnderROC()

    println(s"Area under ROC = $auROC")

//    get accuracy
//    val metricsTest = new MulticlassMetrics(scoreAndLabels)
//    val accuracyTest = metricsTest.accuracy
//    println(s"Accuracy = $accuracyTest")


    // Save and load model
    model.save(sc, "D:\\mydata\\ml\\dt\\decideTree\\tmp\\scalaSVMWithSGDModel")
    val sameModel = SVMModel.load(sc, "D:\\mydata\\ml\\dt\\decideTree\\tmp\\scalaSVMWithSGDModel")
    // $example off$

    sc.stop()
  }
}