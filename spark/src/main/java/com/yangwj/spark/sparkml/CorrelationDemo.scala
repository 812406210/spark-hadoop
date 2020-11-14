package com.yangwj.spark.sparkml

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SQLContext}
/**
  * @author yangwj
  * @date 2020/7/22 15:45
  */
object CorrelationDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("11").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )
    val df = data.map(Tuple1.apply).toDF("features")
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println("Pearson correlation matrix:\n" + coeff1.toString)

    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println("Spearman correlation matrix:\n" + coeff2.toString)
  }
}
