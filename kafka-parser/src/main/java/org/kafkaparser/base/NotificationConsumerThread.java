package org.kafkaparser.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.kafkaparser.utilities.PastieParseAndSearch;
import org.kafkaparser.pojo.Data;

public class NotificationConsumerThread implements Runnable {

  private final KafkaConsumer<String, Data> consumer;
  private final String topic;

  public NotificationConsumerThread(String topic,File configDirectoryfile) throws IOException {
    Properties prop = createConsumerConfig(configDirectoryfile);
    this.consumer = new KafkaConsumer<>(prop);
    this.topic = topic;
    this.consumer.subscribe(Arrays.asList(this.topic));
    
    
  }

  private static Properties createConsumerConfig(File configDirectoryfile) throws IOException {
    Properties properties = new Properties();
          properties.load(new ByteArrayInputStream(Files.readAllBytes(new File(configDirectoryfile, "consumer.properties").toPath())));
          if (properties.getProperty("group.id") == null) {
              properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
          }
    return properties;
  }

  @Override
  public void run() {
	  try  {
			    while (true) 
			    {
			      ConsumerRecords<String, Data> records = consumer.poll(100);
			      for (ConsumerRecord<String, Data> record : records)
				      {
				        /**System.out.println("Receive message: " + record.value() + ", Partition: "
				            + record.partition() + ", Offset: " + record.offset() + ", by ThreadID: "
				            + Thread.currentThread().getId());**/
				    	  	//System.out.println("Message recieved !!*********************************************************"+record.value().getUrl());
				    	  	//Make sure to enable below code for sending resposne and confirming if chanegs has been done or not
				      	    PastieParseAndSearch.searchEachid(record.value());
				      }
			      }
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			}
    }

  
  
  }



