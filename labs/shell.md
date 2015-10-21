# Summary

This document contains all the sample commands used in the Labs sessions along the Workshop. All of them were launched against Spark Shell Session,
learning about which effects has in the [Spark Standalone Cluster](http://spark.apache.org/docs/latest/spark-standalone.html).

We are going to go through 2 different sort of Spark operations:

* **Transformations**. This kind of operations are lazy, which means that they are not going to
run anything in any node until an action is called. They don`t return a value, but a pointer to
a new RDD.
We are going to tag them with a [T] at the beginning of the description to make it as clear as
possible.

* **Actions**. They apply an operation to a RDD and returns a value. When an action is called, all
  the accumulated transformations are applied and the action is run over the last produced one.
  We are going to tag them with a [A] at the beginning of the description to make it as clear as
  possible.
  
In addition, we can follow our process execution, failures, times and some other metrics on the Spark Standalone Cluster Web UI (aka ClusterUI) that is running on localhost:8080 (if you are running our sample scripts)

## Part 1: Basics

* [T] Creates a RDD that will contain an Array[String] with the file lines text.

		val textFile = sc.textFile("data/README.md")
 
* [A] Applies count action to textFile RDD and returns the value.

		textFile.count()
		
* [T] Creates a new RDD with lines that contains the word `Spark`.
    
		val linesWithSpark = textFile.filter(line => line.contains("Spark"))
		
* [T] Creates a new RDD containing the amount of words of each line.

		val lineLenghts = textFile.map(line => line.split(" ").size)

* [A] Compares each lineLength and returns the highest value.

		lineLenghts.reduce((a, b) => if (a > b) a else b)

* [A] Makes the same action as before but using the Math java library.

		import java.lang.Math
		lineLenghts.reduce((a, b) => Math.max(a,b))

* [A] It performs an invalid operation that produces a runtime failure. We can see this sort of errors in the ClusterUI.

		lineLenghts.reduce((a,b) => a+b/0)

* [T] **Map-Reduce**. It will describe a new RDD that will contain the wordCount of each README line.

		val wordCounts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a, b) => a + b)

* [T] It performs the same transformation than the previous one but in a simpler way.

		val words = textFile.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

* [A] We are going to collect the containing values on the wordCounts RDD. We'll see an Array[Int] with all the line wordCounts values.

		wordCounts.collect

* Cache:

		linesWithSpark.cache()

## Part 2: Self Contained Application

This section was run from the System shell:

    cd sampleapp && sbt package

    cd .. &&\ 
    $SPARK_HOME/bin/spark-submit \
    --master spark://localhost:7077 \
    target/scala-2.11/sample-app_2.11-1.0.jar "../data/README.md" \
    --class "SampleApp"


## Part 3: SQL and DataFrames

    import sqlContext.implicits._

  val tweets = sqlContext.jsonFile("data/100tweets.json")
    val positiveWordsFile = sc.textFile("data/positive-words.txt")
    val negativeWordsFile = sc.textFile("data/negative-words.txt")

    val positiveWords = positiveWordsFile.filter(w => !w.startsWith(";"))
    val negativeWords = negativeWordsFile.filter(w => !w.startsWith(";"))

    val pw = positiveWords.collect.toSet
    val nw = negativeWords.collect.toSet

    tweets.printSchema

Running SQL queries:

    tweets.registerTempTable("tweets")

    sqlContext.sql("SELECT text, retweet_count FROM tweets where retweet_count > 0")

Working with DataFrames:

    case class Tweet(text: String, retweet_count: Long, favorited: Boolean)

    val tweetsInfo = tweets.select("text", "retweet_count","favorited") map (t => Tweet(t.getAs[String]("text"), t.getAs[Long]("retweet_count"), t.getAs[Boolean]("favorited")))

    val tweetsInfoDF = tweetsInfo.toDF

    val onlyText = tweetsInfo map (_.text)

    val wordsArray = onlyText map (ot => (ot, ot.split(" ").toSet))

    val positiveTweets = wordsArray map { case (original, set) => set intersect pw filter (!_.isEmpty) map (_ => original) } filter (!_.isEmpty)
    val negativeTweets = wordsArray map { case (original, set) => set intersect nw filter (!_.isEmpty) map (_ => original) } filter (!_.isEmpty)

Persistence:

    negativeWords.persist(org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK)
    negativeWords.collect

#License

Copyright (C) 2015 47 Degrees, LLC [http://47deg.com](http://47deg.com) [hello@47deg.com](mailto:hello@47deg.com)

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
