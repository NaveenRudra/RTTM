package org.kafkaparser.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NotificationConsumerGroup {
  private final int numberOfConsumers;

  private final String topic;
  private List<NotificationConsumerThread> consumers;


  public NotificationConsumerGroup(int numberOfConsumers,String topic,File configDirectoryfile) throws IOException {
    this.topic=topic;
    this.numberOfConsumers = numberOfConsumers;
    consumers = new ArrayList<>();
    for (int i = 0; i < this.numberOfConsumers; i++) {
      NotificationConsumerThread ncThread =
          new NotificationConsumerThread(this.topic,configDirectoryfile);
      consumers.add(ncThread);
    }
  }

  public void execute() {
    for (NotificationConsumerThread ncThread : consumers) {
      Thread t = new Thread(ncThread);
      t.start();
    }
  }


  
  public int getNumberOfConsumers() {
    return numberOfConsumers;
  }





}
