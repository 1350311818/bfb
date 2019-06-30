package com.bfb.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


import java.util.Properties;

public class Kafka<K,V> {
    @Autowired
    Environment ev ;
    Properties properties = null;
    private  String brokeList="";
    public Kafka(){
        this.properties = getProperties();
    }
    private  Properties getProperties(){
        brokeList = ev.getProperty("kafka.brokelist");
        Properties properties=new Properties();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", brokeList);
        return properties;
    }
    public KafkaProducer<K,V>  getProducer(){
        KafkaProducer<K,V> producer = new KafkaProducer<K, V>(this.properties);
        return producer;
    }
    public KafkaConsumer<K,V> getConsumer(){
        properties.put("group.id","group-demo");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaConsumer <K,V> consumer = new KafkaConsumer<K, V>(this.properties);
    return  consumer;
    }
}
