package com.yangwj.spark.sparkml

import org.apache.spark.SparkConf
import org.apache.spark.sql._
/**
  * @author yangwj
  * @date 2020/7/28 9:12
  */
trait SharedSparkContext {


  var spark: SparkSession = {
    val conf = new SparkConf().setAppName("megasparkdiff")
      .setMaster("local[*]")
      .set("spark.driver.host", "localhost")
      .set("spark.ui.enabled", "false") //disable spark UI
    spark = SparkSession.builder.config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("warn")
    spark
  }


  implicit class DataFrameImprovements(df: DataFrame) {
    def getColumnsSeq(): Seq[Column] = {
      val s: Seq[Column] = df.columns.map(c => df(c)).toSeq
      return s
    }
  }

  protected object sqlImplicits extends SQLImplicits {
    protected override def _sqlContext: SQLContext = spark.sqlContext
  }

}
