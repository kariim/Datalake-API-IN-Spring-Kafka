package com.edf.datalake.service.kafka;

import com.edf.datalake.model.KafkaTopic;
import com.edf.datalake.service.dao.TopicRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
import java.util.*;


@Service
public class ConsumerService {

    @Autowired
    private Environment env;

    @Autowired
    private TopicRepository repository;

    private Map<String, KafkaConsumer> consumers;
    private Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private String POLL_TME = "poll.time";
    private JSONParser jsonParser;


    @PostConstruct
    public void initConsumers() {

        final String BOOTStRAP_SERVERS     = "bootstrap.servers";
        final String ZOOKEEPER            = "zookeeper";
        final String GROUP_ID             = "group.id";
        final String KEY_DESERIALIZER     = "key.deserializer";
        final String VALUE_DESERIALIZER   = "value.deserializer";
        final String AUTO_COMMIT          = "enable.auto.commit";
        final String AUTO_COMMIT_INTERVAL = "auto.commit.interval.ms";
        final String SESSION_TIMEOUT      = "session.timeout.ms";

        consumers = new HashMap<>();
        jsonParser = new JSONParser();

        Properties config = new Properties();

        config.put(BOOTStRAP_SERVERS, env.getProperty(BOOTStRAP_SERVERS));
        config.put(ZOOKEEPER, env.getProperty(ZOOKEEPER));
        config.put(AUTO_COMMIT, env.getProperty(AUTO_COMMIT));
        config.put(AUTO_COMMIT_INTERVAL, env.getProperty(AUTO_COMMIT_INTERVAL));
        config.put(SESSION_TIMEOUT, env.getProperty(SESSION_TIMEOUT));
        config.put(KEY_DESERIALIZER, env.getProperty(KEY_DESERIALIZER));
        config.put(VALUE_DESERIALIZER, env.getProperty(VALUE_DESERIALIZER));

        for(KafkaTopic topic : repository.findAll()) {
            config.put(GROUP_ID, topic.getId());
            KafkaConsumer consumer = new KafkaConsumer<String, String>(config);
            consumer.subscribe(Arrays.asList( topic.getId() ));

            consumers.put(topic.getId(), consumer);
        }
    }

    public List<JSONObject> getMessages(String topic, String apiKey) {
        KafkaConsumer consumer = consumers.get(topic);
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

}



