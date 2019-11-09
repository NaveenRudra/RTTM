RTTM allows you to monitor open source data available in the wild and updated you with an alert. If a releavant message is found.

* In order to use this first you need to understand bit abour Kafka and create a topic
* Make sure Jdk 1.8 is installed
* If you do not want to go through all the dependency issues, build the docker file. It will have all the needed software.

In order to run RTTM, pass the respective argument. For example,
```
java -jar <path to>/scraptool-1.0-SNAPSHOT-standalone.jar -t <kafka topic name> -c <config directory full path>
```

