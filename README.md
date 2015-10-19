# Spark Workshop

This repository was delivered at [Lambda World 2015](http://www.lambda.world/) on October 23th, 2015, celebrated in Cádiz, as part of one the Workshops of Introduction to [Apache Spark](http://spark.apache.org/).

## Overview

>Apache Spark is a fast and general-purpose cluster computing system. It provides high-level APIs in Java, Scala, Python and R, and an optimized engine that supports general execution graphs. It also supports a rich set of higher-level tools including Spark SQL for SQL and structured data processing, MLlib for machine learning, GraphX for graph processing, and Spark Streaming.

[Apache Spark](http://spark.apache.org/)

## Prerequisites

Some experience in Scala. Some familiarity with big data or parallel processing concepts would be helpful.

## Road Map

* Intro Big Data and Spark
* Spark Architecture
* Resilient Distributed Datasets (RDDs)
	* Labs Session
* Transformations and Actions on Data using RDDs
	* Labs Session
* Overview Spark SQL and DataFrames
	* Labs Session
* Overview Spark Streaming
* Spark Architecture and Cluster Deployment

## How this repo is organized

* [/data](./data): contains some files that will be used along the workshop as a sample data.
* [/dist](./dist): binaries and config files that would be useful to setup the Spark cluster locally. We'll see how to use them in the next section.
* [/labs](./labs): contains all the commands used along the workshop with some details.
* [/sampleapp](./sampleapp): scala application used to package and submit to the Spark Cluster with _spark-submit_ command.
* [/sbt](./sbt): sbt binaries, useful to work with the *sampleapp* scala application.
* [/scripts](./scripts): contains the shell script to run the Spark Cluster locally, initially with a Spark Master and two Spark Worker nodes.

## Setup Locally

* Install [Oracle Java 7/8](https://www.oracle.com/java/index.html) in case you haven't yet.
* Download Spark binary distribution. In this workshop we'll use [Spark 1.5.1 Pre-built for Hadoop 2.4](http://d3kbcqa49mib13.cloudfront.net/spark-1.5.1-bin-hadoop2.4.tgz).
* Uncompress the file in your local machine and set up the **SPARK_HOME** environment variable, with the whole path. For instance:
    `export SPARK_HOME=/path/to/spark-1.5.1-bin-hadoop2.4`
* File [/data/spark-env.sh](./dist/spark-env.sh) must be copied to `$SPARK_HOME/spark-env.sh`.
* Let's run the Spark Cluster and the Spark Shell, altogether into a single step:
    `sh scripts/sparkws.sh`
* That's all, if everything was fine you should be able to see the local Spark Standalone Deployment into your browser: http://localhost:8080 .

![" "](http://jpedrom.es/1PuYcKF "Spark Standalone Deployment")

## About Tweets Sentimental Analysis

The list of negative words and positive words used in this repository are based on these papers:

- Minqing Hu and Bing Liu. "Mining and Summarizing Customer Reviews."
      Proceedings of the ACM SIGKDD International Conference on Knowledge
      Discovery and Data Mining (KDD-2004), Aug 22-25, 2004, Seattle,
      Washington, USA,
- Bing Liu, Minqing Hu and Junsheng Cheng. "Opinion Observer: Analyzing
      and Comparing Opinions on the Web." Proceedings of the 14th
      International World Wide Web conference (WWW-2005), May 10-14,
      2005, Chiba, Japan.

Those are used just for sample demo purposes.

Notes:

1. The appearance of an opinion word in a sentence does not necessarily
  mean that the sentence expresses a positive or negative opinion.
  See the paper below:

	Bing Liu. "Sentiment Analysis and Subjectivity." An chapter in
  	Handbook of Natural Language Processing, Second Edition,
 	(editors: N. Indurkhya and F. J. Damerau), 2010.
2. You will notice many misspelled words in the list. They are not
  mistakes. They are included as these misspelled words appear
  frequently in social media content.

#License

Copyright (C) 2015 47 Degrees, LLC [http://47deg.com](http://47deg.com) [hello@47deg.com](mailto:hello@47deg.com)

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
