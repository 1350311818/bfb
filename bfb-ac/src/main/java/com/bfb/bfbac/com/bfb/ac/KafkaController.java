package com.bfb.bfbac.com.bfb.ac;

import com.bfb.kafka.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @GetMapping("/kafka/{id}")
    public  String findById(@PathVariable long id){
        KafkaProducer<String,String> producer = null;
        try{
            Kafka kafka=new Kafka();
            producer= kafka.getProducer();
            int i = 0;
            while (i<1000){
                ProducerRecord<String,String> record=new ProducerRecord<>("topic-demo","hello"+i);
                producer.send(record);
                i++;
            }
        }finally {
           if(producer != null){
               producer.close();
           }
        }

        return  "sucess";
    }
}
