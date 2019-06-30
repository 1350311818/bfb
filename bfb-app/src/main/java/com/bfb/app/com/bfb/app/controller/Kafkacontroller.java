package com.bfb.app.com.bfb.app.controller;

import com.bfb.kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
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
        while (true){
            ConsumerRecords<String, String> records= this.consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String,String> record:records){
               long offSeek = record.offset();
                System.out.println(offSeek);
                ProducerRecord<String,String> producerRecord=new ProducerRecord<>("topic-demo-test","hello"+offSeek);
                producer.send(producerRecord,Callback());
            }
        }
    }
    private Callback Callback(){
        Callback callback = new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                int partition =recordMetadata.partition();
                TopicPartition topicPartition =  new TopicPartition("topic-demo",partition);
                long offSet = recordMetadata.offset();
                if(offSet == 999){
                    consumer.seek(topicPartition,offSet);
                }
            }
        };
        return callback;
    }
}
