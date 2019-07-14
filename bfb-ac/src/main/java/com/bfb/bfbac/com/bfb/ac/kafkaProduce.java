package com.bfb.bfbac.com.bfb.ac;

import com.bfb.kafka.Kafka;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class kafkaProduce {
    public  void  test(){
        Kafka kafka=new Kafka();
        KafkaProducer<String,String> producer = kafka.getProducer();
        int i = 0;
        while (i<1000){
            ProducerRecord<String,String> record=new ProducerRecord<>("topic-demo","hello"+i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(recordMetadata ==null){
                        System.out.println(e);
                    }else {
                        System.out.println(recordMetadata.offset());
                    }
                }
            });
            i++;
        }
        producer.close();
    }

    public static void main(String[] args) {
        kafkaProduce k=  new  kafkaProduce();
        k.test();
    }
}
