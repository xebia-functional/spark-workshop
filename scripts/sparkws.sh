#!/usr/bin/env bash

# Stop the current instances:
sh $SPARK_HOME/sbin/stop-master.sh
sh $SPARK_HOME/sbin/stop-slave.sh

# Start the master:
sh $SPARK_HOME/sbin/start-master.sh -i 127.0.0.1
# Start the slaves:
sh $SPARK_HOME/sbin/start-slave.sh spark://127.0.0.1:7077

# Start the shell:
sh $SPARK_HOME/bin/spark-shell --master spark://127.0.0.1:7077 --total-executor-cores 4
