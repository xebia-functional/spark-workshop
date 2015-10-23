# Summary

This document contains all the sample commands used in the Labs sessions along the Workshop. All of them were launched against Spark Shell Session,
learning about which effects has in the [Spark Standalone Cluster](http://spark.apache.org/docs/latest/spark-standalone.html).

## Part 1: Basics

We are going to go through 2 different sort of Spark operations:

* **Transformations**. This kind of operations are lazy, which means that they are not going to
run anything in any node until an action is called. They don't return a value, but a pointer to
a new RDD.
We are going to tag them with a [T] at the beginning of the description to make it as clear as
possible.

* **Actions**. They apply an operation to a RDD and returns a value. When an action is called, all
  the accumulated transformations are applied and the action is run over the last produced one.
  We are going to tag them with a [A] at the beginning of the description to make it as clear as
  possible.

In addition, we can follow our process execution, failures, times and some other metrics on the Spark Standalone Cluster Web UI (aka ClusterUI) that is running on localhost:8080 (if you are running our sample scripts)

*Exercises*

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

* [A] It performs an invalid operation that produces a runtime failure. The exception is initially wrapped into a lazy transformation operation. We only will see the issue running an action (collect in the example). We can see this sort of errors in the ClusterUI.

        val lazyFailure = lineLenghts.map(len => len + 3 / 0)
		lazyFailure.collect()

* [A] It saves the `lineLengths` RDD as a text file, using string representations of elements.

        lineLenghts.saveAsTextFile("tmp/lineLengths")

* [T] **Map-Reduce**. It will describe a new RDD that will contain the wordCount of each README line.

		val wordCounts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a, b) => a + b)

* [T] It performs the same transformation than the previous one but in a simpler way.

		val words = textFile.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

* [A] We are going to collect the containing values on the wordCounts RDD. We'll see an Array[Int] with all the line wordCounts values.

		wordCounts.collect

* `Cache` method, it persists the RDD with the default storage level (`MEMORY_ONLY`):

		val cachedRDD = wordCounts.cache()
        cachedRDD.collect()

	Once the wordCounts RDD is cached, it won't be re-computed (have a look to the ClusterUI to see more details).

* Utility to show more information about one RDD from the Spark Shell:

        cachedRDD.toDebugString

## Part 2: Self Contained Application

In this section we are going to deploy an app to a Spark cluster.

*Exercises*

* Navigate to the sampleApp folder and create the dist package.
		
		cd sampleapp && ../sbt/bin/sbt package

* Submits the dist package to the master spark node, requesting 4 CPU cores along all the available executors and specifying the main class name of your app.

		$SPARK_HOME/bin/spark-submit \ 
		--master spark://127.0.0.1:7077 \
		--total-executor-cores 4 \
		target/scala-2.11/sample-app_2.11-1.0.jar "../data/README.md" \
		--class "SampleApp"


## Part 3: SQL and DataFrames

This section will guide you through some SparkSQL examples. All the resources files are a folder included in this repo called `data`.

*Exercises*

* We are going to load 3 different files, one that contains 100 different tweets, one that contains positive words, and another one that contains negative words. 

		import sqlContext.implicits._

		val tweets = sqlContext.jsonFile("data/100tweets.json")
		val positiveWordsFile = sc.textFile("data/positive-words.txt")
		val negativeWordsFile = sc.textFile("data/negative-words.txt")

* Then, we are filtering positive and negative words files to get only those of them that aren't comments and are not empty.    

		val positiveWords = positiveWordsFile.filter(w => !w.startsWith(";")).filter(!_.isEmpty)
		val negativeWords = negativeWordsFile.filter(w => !w.startsWith(";")).filter(!_.isEmpty)

		val pw = positiveWords.collect.toSet
		val nw = negativeWords.collect.toSet

* It prints the schema that is auto inferred from the json file content.

		tweets.printSchema

* Running SQL queries:

		tweets.registerTempTable("tweets")

		sqlContext.sql("SELECT text, retweet_count FROM tweets where retweet_count > 0")

* Working with DataFrames. We are defining a case class and use to create new objects. We are making the tweets content to fit in that case class.

		case class Tweet(text: String, retweet_count: Long, favorited: Boolean)

		val tweetsInfo = tweets.select("text", "retweet_count","favorited") map (t => Tweet(t.getAs[String]("text"), t.getAs[Long]("retweet_count"), t.getAs[Boolean]("favorited")))

* Creates a DataFrame from the RDD.

		val tweetsInfoDF = tweetsInfo.toDF

* Defines a RDD that contains only the tweet text, transform it to get a new one with all the words and then, use it to get positive and negative tweets.

		val onlyText = tweetsInfo map (_.text)

		val wordsArray = onlyText map (ot => (ot, ot.split(" ").toSet))

		val positiveTweets = wordsArray flatMap { case (original, set) => set intersect pw filter (!_.isEmpty) map (_ => original) } filter (!_.isEmpty)
		val negativeTweets = wordsArray flatMap { case (original, set) => set intersect nw filter (!_.isEmpty) map (_ => original) } filter (!_.isEmpty)

* **Persistence**. We are going to save our positive and negative tweets in 2 different ways. Positive will be stored in both, memory and disk, as plain text. Negative ones will be persisted in both too, memory and disk, but the content will be serialised.

		positiveWords.persist(org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK)
		positiveWords.collect
    		negativeWords.persist(org.apache.spark.storage.StorageLevel.MEMORY_AND_DISK_SER)
		negativeWords.collect