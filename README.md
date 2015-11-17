# Spark Workshop

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
  * Labs Session
* Spark Architecture and Cluster Deployment

## How this repo is organized

* [/data](./data): contains some files that will be used along the workshop as a sample data.
* [/notebooks](./notebooks): contains all the notebooks used along the workshop with some additional details.
* [/sampleapp](./sampleapp): scala application used to package and submit to the Spark Cluster with _spark-submit_ command.
* [/sbt](./sbt): sbt binaries, useful to work with the *sampleapp* scala application.

## Setup Locally

* Install [Oracle Java 7/8](https://www.oracle.com/java/index.html) in case you haven't yet.
* Clone this repository in your machine: `git clone https://github.com/47deg/spark-workshop.git`
* Download Spark Notebook binary distribution:

```bash
wget -O spark-notebook.tgz https://s3.eu-central-1.amazonaws.com/spark-notebook/tgz/spark-notebook-master-scala-2.10.4-spark-1.5.1-hadoop-2.2.0.tgz?max-keys=100000
```
* Decompress the downloaded file as `spark-notebook.tgz`:

```bash
tar -zxvf spark-notebook.tgz
```

* Rename the directory to make it more friendly:

```bash
mv spark-notebook-0.6.2-SNAPSHOT-scala-2.10.4-spark-1.5.1-hadoop-2.2.0 spark-notebook
```

* Create a directory where to copy the workshop notebooks:

```bash
mkdir spark-notebook/notebooks/workshop
cp notebooks/* spark-notebook/notebooks/workshop
```

* Run the Spark Notebook:

```bash
cd spark-notebook && bin/spark-notebook
```

* That's all, if everything was fine you should be able to see the Spark Notebook UI on your browser: [http://localhost:9000/tree/workshop](http://localhost:9000/tree/workshop) .

## Acknowledgements

Special thanks to @andypetrella for the excellent work done on the [spark-notebook](https://github.com/andypetrella/spark-notebook) project.

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
