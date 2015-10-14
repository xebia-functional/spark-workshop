package com.fortysevendeg

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SampleApp {
  def main(args: Array[String]) {

    if (args.length <= 1)
      throw new IllegalArgumentException("You must specify the file as first argument!")

    val logFile = args(0)

    val conf = new SparkConf()
        .setAppName("Sample Application")
        .set("spark.eventLog.enabled", "true")
        .set("spark.eventLog.dir", "logs")

    val sc = new SparkContext(conf)

    val logData: RDD[String] = sc.textFile(logFile, 2).cache()
    val scalaRDD: RDD[String] = logData.filter(_.contains("Scala"))
    val javaRDD: RDD[String] = logData.filter(_.contains("Java"))

    val scalaAction: Long = scalaRDD.count()
    val javaAction: Long = javaRDD.count()

    println(s"\n\n" +
        s"Lines with 'Scala': $scalaAction" +
        s"\n" +
        s"Lines with 'Java': $javaAction" +
        s"\n\n")
  }
}