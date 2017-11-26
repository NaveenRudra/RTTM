package com.realtimescrapper.base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import com.relatimescrapper.pojo.Data;

public class Producer {

	private static KafkaProducer<String, Data> producer;
	public static void initialize(File configDirectory) throws IOException
	{
		 
	            Properties properties = new Properties();
	            properties.load(new ByteArrayInputStream(Files.readAllBytes(new File(configDirectory, "producer.properties").toPath())));
	            producer = new KafkaProducer<>(properties);
	}
	
	public static void send(Data data,String topic)
	{
		//do parsing of the urls from regex here
		try {
			
			   producer.send(new ProducerRecord<String, Data>(topic, data), new Callback() {
			        public void onCompletion(RecordMetadata metadata, Exception e) {
			            if (e != null) {
			              e.printStackTrace();
			            }
			           // System.out.println("Sent: Partition: " + metadata.partition() + ", Offset: "
			            //    + metadata.offset());
			          }
			        });
			} catch (Exception e) {
			   e.printStackTrace();
			}
		
	}
}
