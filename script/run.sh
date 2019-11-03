#!/bin/bash

# Update server.properties when executing
# env var will set when executing docker run
if [ ! -z "$ADVERTISED_HOST" ]; then
    sed -r -i "s/#(advertised.host.name)=(.*)/\1=$ADVERTISED_HOST/g" config/server.properties
fi

if [ ! -z "$ADVERTISED_PORT" ]; then
    sed -r -i "s/#(advertised.port)=(.*)/\1=$ADVERTISED_PORT/g" config/server.properties
fi

if [ ! -z "${NUM_PARTITIONS}" ]; then
    sed -r -i "s/(num.partitions)=(.*)/\1=${NUM_PARTITIONS}/g" config/server.properties
fi

#delete topic
/opt/kafka/bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic test

sleep 2

#stop kafka server
/opt/kafka/bin/kafka-server-stop.sh

sleep 5
#stop zookeeper
/opt/kafka/bin/zookeeper-server-stop.sh

sleep 1

rm -rf /tmp/kafka-logs
rm -rf /opt/kafka/logs

# Start to run zookeeper as background process
/opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties &

sleep 3

# Start kafka server
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties &

seelp 4

#Create a topic
/opt/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test


#Start mysql server
service mysql start

exit
