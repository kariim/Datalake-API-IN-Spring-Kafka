package com.edf.datalake.service.kafka;

import com.edf.datalake.model.Topic;
import com.edf.datalake.service.dao.TopicRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.WakeupException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;


@Service
public class ProducerService {

    @Autowired
    private Environment env;

    @Autowired
    private TopicRepository repository;

    private Map<String, KafkaProducer> producers;
    private Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private JSONParser jsonParser;


    @PostConstruct
    public void initProducers() {

        final String SECURITY_LOGIN       = "java.security.auth.login.config";
        final String SECURITY_KRB5        = "java.security.krb5.conf";
        final String CLIENT_ID            = "client.id";
        final String BOOTSTRAP_SERVERS    = "bootstrap.servers";
        final String ZOOKEEPER            = "zookeeper";
        final String GROUP_ID             = "group.id";
        final String SECURITY_PROTOCOL    = "security.protocol";
        final String TRUSTSTORE_LOCATION  = "ssl.truststore.location";
        final String TRUSTSTORE_PASSWORD  = "ssl.truststore.password";
        final String KEY_DESERIALIZER     = "key.deserializer";
        final String VALUE_DESERIALIZER   = "value.deserializer";
        final String AUTO_COMMIT          = "enable.auto.commit";
        final String AUTO_COMMIT_INTERVAL = "auto.commit.interval.ms";
        final String SESSION_TIMEOUT      = "session.timeout.ms";

        producers = new HashMap<>();
        jsonParser = new JSONParser();

        Properties config = new Properties();

        System.setProperty(SECURITY_LOGIN, env.getProperty(SECURITY_LOGIN));
        System.setProperty(SECURITY_KRB5, env.getProperty(SECURITY_KRB5));

        config.put(CLIENT_ID, env.getProperty(CLIENT_ID));
        config.put(BOOTSTRAP_SERVERS, env.getProperty(BOOTSTRAP_SERVERS));
        config.put(ZOOKEEPER, env.getProperty(ZOOKEEPER));
        config.put(SECURITY_PROTOCOL, env.getProperty(SECURITY_PROTOCOL));
        config.put(TRUSTSTORE_LOCATION, env.getProperty(TRUSTSTORE_LOCATION));
        config.put(TRUSTSTORE_PASSWORD, env.getProperty(TRUSTSTORE_PASSWORD));
        config.put(AUTO_COMMIT, env.getProperty(AUTO_COMMIT));
        config.put(AUTO_COMMIT_INTERVAL, env.getProperty(AUTO_COMMIT_INTERVAL));
        config.put(SESSION_TIMEOUT, env.getProperty(SESSION_TIMEOUT));
        config.put(KEY_DESERIALIZER, env.getProperty(KEY_DESERIALIZER));
        config.put(VALUE_DESERIALIZER, env.getProperty(VALUE_DESERIALIZER));

        for(Topic topic : repository.findAll()) {

        }
    }

    public List<JSONObject> getMessages(String topic) {
        KafkaConsumer consumer = producers.get(topic);
        List<JSONObject> results = new ArrayList<>();

        try {

            ConsumerRecords<String, String> records = consumer.poll( Long.valueOf(env.getProperty(POLL_TME)) );

            for (ConsumerRecord<String, String> record : records) {
                logger.info(record.value());

                JSONObject result = (JSONObject) jsonParser.parse(record.value());
                results.add( result );

                logger.info("Entry : " + result.toJSONString());
            }

        } catch (WakeupException e) {
            logger.error(e.getMessage());
        } catch (ConcurrentModificationException e) {
            logger.error("Im fucking busy");
        } catch (ParseException e) {
            logger.error("Impossible to parse entry to JSON");
        }

        return results;
    }

    @PreDestroy
    public void cleanup() {

    }

}



