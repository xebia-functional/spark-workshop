#!/usr/bin/env bash

# Stop the current instances:
sh $SPARK_HOME/sbin/stop-master.sh
sh $SPARK_HOME/sbin/stop-slave.sh

# Start the master:
sh $SPARK_HOME/sbin/start-master.sh
# Start the slaves:
sh $SPARK_HOME/sbin/start-slave.sh spark://localhost:7077

# Start the shell:
sh $SPARK_HOME/bin/spark-shell --master spark://localhost:7077 --total-executor-cores 4
