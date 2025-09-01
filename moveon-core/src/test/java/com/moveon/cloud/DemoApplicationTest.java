package com.moveon.cloud;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName DemoApplicationTest
 * @Description TODO
 * @Author huangzh
 * @Date 2025/9/1 14:41
 * @Version 1.0
 */
@SpringBootTest
public class DemoApplicationTest {

    public static final Logger logger = LoggerFactory.getLogger(DemoApplicationTest.class);

    @Test
    public void contextLoads() throws Exception{
        // 初始化生产者
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroup");
        // 设置NameServer地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动生产者
        producer.start();

        // 创建消息
        Message msg = new Message("TestTopic",
                "TestTag",
                ("Hello RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET));

        // 发送消息
        SendResult sendResult = producer.send(msg);
        System.out.printf("Send result: %s%n", sendResult);

        // 关闭生产者
        producer.shutdown();
    }

    @Test
    public void testConsumer() throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("TestTopic", "*");

        // 注册消息监听器
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                System.out.printf("Received message: %s%n", new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println("Consumer started.");
    }
}
