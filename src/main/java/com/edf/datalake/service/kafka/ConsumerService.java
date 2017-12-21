package com.edf.datalake.service.kafka;

import com.edf.datalake.model.entity.KafkaTopic;
import com.edf.datalake.service.dao.TopicRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
public class ConsumerService {

    @Autowired
    private Environment env;

    @Autowired
    private TopicRepository repository;

    private Map<String, KafkaConsumer> consumers;
    private final Long pollTime = 2000L;
    private Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @PostConstruct
    public void initConsumers() {

        final String LOGIN_CONFIG         = "java.security.auth.login.config";
        final String CLIENT_ID            = "client.id";
        final String SERVERS              = "bootstrap.servers";
        final String ZOOKEEPER            = "zookeeper";
        final String SECURITY_PROTOCOL    = "security-protocol";
        final String TRUSTSTORE_LOCATION  = "ssl.truststore.location";
        final String TRUSTSTORE_PASSWORD  = "ssl.truststore.password";
        final String GROUP_ID             = "group.id";
        final String KEY_DESERIALIZER     = "key.deserializer";
        final String VALUE_DESERIALIZER   = "value.deserializer";

        consumers = new HashMap<>();

        System.setProperty(LOGIN_CONFIG, env.getProperty(LOGIN_CONFIG));

        Properties config = new Properties();
        config.put(CLIENT_ID, env.getProperty(CLIENT_ID));
        config.put(SERVERS, env.getProperty(SERVERS));
        config.put(ZOOKEEPER, env.getProperty(ZOOKEEPER));
        config.put(SECURITY_PROTOCOL, env.getProperty(SECURITY_PROTOCOL));
        config.put(TRUSTSTORE_LOCATION, env.getProperty(TRUSTSTORE_LOCATION));
        config.put(TRUSTSTORE_PASSWORD, env.getProperty(TRUSTSTORE_PASSWORD));
        config.put(GROUP_ID, env.getProperty(GROUP_ID));
        config.put(KEY_DESERIALIZER, env.getProperty(KEY_DESERIALIZER));
        config.put(VALUE_DESERIALIZER, env.getProperty(VALUE_DESERIALIZER));

        for(KafkaTopic topic : repository.findAll()) {
            KafkaConsumer consumer = new KafkaConsumer<String, String>(config);
            consumer.subscribe(Arrays.asList( topic.getId() ));

            consumers.put(topic.getId(), consumer);
        }
    }

    public void getMessages(String topic, String apiKey) {
        KafkaConsumer consumer = consumers.get(topic);

        try {
            ConsumerRecords<String, String> records = consumer.poll(pollTime);
            for (ConsumerRecord<String, String> record : records) {
                Map<String, Object> data = new HashMap<>();
                data.put("partition", record.partition());
                data.put("offset", record.offset());
                data.put("value", record.value());

                logger.info("Entry : " + data);
            }

        } catch (WakeupException e) {
            logger.error(e.getMessage());

        } finally {
            logger.info("Closing the Kafka customer");
            consumer.close();
        }
    }

}
