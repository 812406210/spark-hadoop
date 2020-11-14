package com.yangwj.spark.sparkml

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.{DecisionTreeClassificationModel, DecisionTreeClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature._
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @author yangwj
  * @date 2020/7/28 9:10
  */
object DTShapeTypeExample extends SharedSparkContext {

  import sqlImplicits._

  def main(args: Array[String]): Unit = {

    val data: DataFrame = Seq(
      (9,"BabyChair"),
      (10,"BabyChair"),
      (11,"BabyChair"),
      (12,"BabyChair"),
      (16,"chair"),
      (17,"chair"),
      (18,"chair"),
      (19,"chair"),
      (18,"chair"),
      (29,"table"),
      (30,"table"),
      (31,"table"),
      (29,"table"),
      (30,"table"),
      (31,"table")).toDF("height","shape")
    println("data show------"+data.show())

    // Index labels, adding metadata to the label column.
    // Fit on whole dataset to include all labels in index.
    // labelIndexer = [table,chair,BabyChair]
    //标签
    val labelIndexer = new StringIndexer()
      .setInputCol("shape")
      .setOutputCol("indexedLabel")
      .fit(data)
    println("labelIndexer show------"+ labelIndexer.labels.mkString)


    //Continous Features
    val continousFeatures = Seq("height")
    println("continousFeatures show------"+ continousFeatures.mkString)

    //height,features
    //特征数据
    val featureAssembler = new VectorAssembler()
      .setInputCols(continousFeatures.toArray)
      .setOutputCol("features")
    println("featureAssembler show------"+ featureAssembler.outputCol.parent.mkString)


    // Split the data into training and test sets (30% held out for testing).
    //数据切分
    val Array(trainingData, testData) = data.randomSplit(Array(0.8, 0.2))
    println("trainingData===="+trainingData.show())
    // Train a DecisionTree model.
    val dt = new DecisionTreeClassifier()
      .setLabelCol("indexedLabel")
      .setFeaturesCol("features")

    // Convert indexed labels back to original labels.
    // 将数字标签转为原始标签(table,chair,BabyChair)
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)
    println("labelConverter===="+labelConverter.extractParamMap().toSeq.foreach(x=>{
        println("param==="+x.param+"====value==="+x.value.toString)
    }))

    // Chain indexers and tree in a Pipeline.
    val pipeline = new Pipeline()
                        //标签          特征数据   决策树模型    标签转化(0-->table,1-->chair,2-->BabyChair)
      .setStages(Array(labelIndexer,featureAssembler, dt, labelConverter))

    // Train model. This also runs the indexers.
    val model: PipelineModel = pipeline.fit(trainingData)

    // Make predictions.
    val predictions = model.transform(testData)

    // Select example rows to display.
    predictions.select("predictedLabel", "indexedLabel", "features").show(false)

    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    val treeModel = model.stages(2).asInstanceOf[DecisionTreeClassificationModel]
    println(s"Learned classification tree model:\n ${treeModel.toDebugString}")
    // $example off$

    //save model  保存模型
    //model.save("D:\\mydata\\ml\\dt\\decideTree");
    // load model 加载模型/
    //val sameModel = DecisionTreeClassifier.load("D:\\mydata\\ml\\dt\\decideTree")
    spark.stop()
  }
}