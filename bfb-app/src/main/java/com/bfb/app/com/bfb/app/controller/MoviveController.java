package com.bfb.app.com.bfb.app.controller;

import com.bfb.kafka.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviveController {
//    @Autowired
//    private RedisLocker RedisLocker;
//    @Autowired
//    private RedisLock redisLock;
    @GetMapping("/user/{id}")
    public String findText(){
//       RLock rLock= redisLock.getLock("test");
//        try {
//            RedisLocker.lock(rLock,new AquiredLockWorker<Object>(){
//
//                @Override
//                public Object invokeAfterLockAquire() throws Exception {
//                    System.out.println("执行方法！");
//
//                    return null;
//                }
//
//                @Override
//                public Object invokeAfterLockFail()  {
//                    System.out.println("执锁失败");
//                    return null;
//                }
//            });
//        } catch (Exception e) {
//            return  "fail";
//
//
//        }finally {
//            rLock.unlock();
//        }
        Kafka kafka = new Kafka();
        KafkaProducer<String, String>  producer = kafka.getProducer();
        ProducerRecord<String,String> record = new ProducerRecord<>("topic-demo","hello");
        producer.send(record);
        return  "test";
    }
}
