## Part 2: Self Contained Application

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