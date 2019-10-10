package org.kafkaparser.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.kafkaparser.utilities.ConfigData;
import org.kafkaparser.utilities.PastieParseAndSearch;
import org.kafkaparser.pojo.Data;

public class NotificationConsumerThread implements Runnable {

  private final KafkaConsumer<String, Data> consumer;
  private final String topic;
  private static List<String> userAgents = new ArrayList<>();


  public static void initialize(String configDirectory)
  {
	  
	  try {
		 //userAgents = Files.readAllLines(Paths.get(ConfigData.configDirectory,ConfigData.useragents_listPropertiesFileName),
		 //         Charset.defaultCharset());
		  //static block is initilized before initilaizing variables is causing issue. Danger comment
		  ConfigData.userAgents = Files.readAllLines(Paths.get(ConfigData.configDirectory,ConfigData.useragents_listPropertiesFileName),
		          Charset.defaultCharset());
		  Properties prop = getConfig(new File(configDirectory),"global.properties");
		  ConfigData.pythonPath = prop.getProperty("pythonpath");
		  ConfigData.trufflehogPath = prop.getProperty("trufflehogpath");
		  //ConfigData.pythonPath = prop.getProperty("");
		  
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  
	 
  }
  
  public NotificationConsumerThread(String topic,File configDirectoryfile) throws IOException {
    Properties prop = getConfig(configDirectoryfile,"consumer.properties");
    this.consumer = new KafkaConsumer<>(prop);
    this.topic = topic;
    this.consumer.subscribe(Arrays.asList(this.topic));
    
    initialize(configDirectoryfile.getAbsolutePath());
    
    
     
  }

  private static Properties getConfig(File configDirectoryfile,String propFileName) throws IOException {
	  Properties properties = new Properties();
          properties.load(new ByteArrayInputStream(Files.readAllBytes(new File(configDirectoryfile, propFileName).toPath())));
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
			    	  		Thread.sleep(200);
				      	    PastieParseAndSearch.searchEachid(record.value());
				      }
			      }
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			}
    }

  
  
  }



