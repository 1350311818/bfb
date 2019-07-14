package com.bfb.app.com.bfb.app.controller;

import com.bfb.kafka.Kafka;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;

@RestController
public class Kafkacontroller {
    KafkaConsumer<String,String> consumer = null;
    KafkaProducer<String,String> producer = null;
    Kafka Kafka = null;
    @GetMapping("/kafkaTest/{id}")
    public  String findById(@PathVariable long id){

        try{
             Kafka = new Kafka();
            consumer();
         }finally {
            if(consumer != null){
                consumer.close();
            }
            if(producer !=null){
                producer.close();
            }
         }
        return  "sucess";
    }
    private  void consumer(){
        this.consumer = Kafka.getConsumer();
        this.consumer.subscribe(Arrays.asList("topic-demo"));
        KafkaProducer<String,String> producer = Kafka.getProducer();
            ConsumerRecords<String, String> records= this.consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String,String> record:records){
               long offSeek = record.offset();
                System.out.println("-----"+offSeek);
//                ProducerRecord<String,String> producerRecord=new ProducerRecord<>("test","hello"+offSeek);
//                producer.send(producerRecord,Callback());
                if(offSeek==9200){
                    TopicPartition topicPartition =  new TopicPartition("topic-demo",record.partition());
                   consumer.seek(topicPartition,9004);
                }

        }
    }
    private Callback Callback(){
        Callback callback = new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(recordMetadata.offset()==6249){
                    int partition =recordMetadata.partition();
                    TopicPartition topicPartition =  new TopicPartition("topic-demo",partition);
                    List<PartitionInfo> t = consumer.partitionsFor(recordMetadata.topic());
                    List<TopicPartition> t1 =new ArrayList<>();
                    Map<TopicPartition,Long> mt=new HashMap();
                    for (PartitionInfo info: t){
                        t1.add(new TopicPartition(info.topic(),info.partition()));
                        mt.put(new TopicPartition(info.topic(),info.partition()),recordMetadata.timestamp());
                    }

                    Map<TopicPartition, OffsetAndTimestamp> result=consumer.offsetsForTimes(mt);
                    Map<TopicPartition, OffsetAndMetadata> result1=new HashMap<>();
                    for (TopicPartition partition1 : mt.keySet()){
                        long offset =result.get(partition1).offset();
                        OffsetAndMetadata OffsetAndMetadata= new OffsetAndMetadata(offset);
                        consumer.seek(partition1,OffsetAndMetadata);
                    }
                    consumer.commitSync(result1);
                }
            }
        };
        return callback;
    }

    public static void main(String[] args) {
      new  Kafkacontroller ().findById(1);
    }
}
