#!/bin/bash

# Update server.properties when executing with delete.topic.enable=true
# env var will set when executing docker run


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

git config --global user.email "test@example.com"



exit
