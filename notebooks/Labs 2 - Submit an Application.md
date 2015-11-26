## Part 2: Self Contained Application

In this section we are going to deploy an app to a Spark cluster.

*Exercises*

* Download and install Spark locally (Spark Docs)[http://spark.apache.org/downloads.html]:

* Set up an environment variable `SPARK_HOME` pointing to your Spark sources:

		export SPARK_HOME=/path/to/spark-1.5.1-bin-hadoop2.4

* Start the Standalone Spark Cluster:

		$SPARK_HOME/sbin/start-master.sh -i 127.0.0.1
		$SPARK_HOME/sbin/start-slave.sh spark://127.0.0.1:7077

* Navigate to the sampleApp folder and create the dist package.

    	cd sampleapp && ../sbt/bin/sbt package

* Submits the dist package to the master spark node, requesting 4 CPU cores along all the available executors and specifying the main class name of your app.

    	$SPARK_HOME/bin/spark-submit \
    	--master spark://127.0.0.1:7077 \
    	--total-executor-cores 4 \
    	target/scala-2.11/sample-app_2.11-1.0.jar "../data/README.md" \
    	--class "SampleApp"

* Stop the Cluster:

    	$SPARK_HOME/sbin/stop-master.sh
		$SPARK_HOME/sbin/stop-slave.sh