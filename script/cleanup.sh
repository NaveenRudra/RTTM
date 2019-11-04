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