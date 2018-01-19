package com.edf.datalake.service.kafka;

import com.edf.datalake.model.dto.*;
import com.edf.datalake.model.entity.ApiKey;
import com.edf.datalake.model.entity.Topic;
import com.edf.datalake.service.dao.ApiKeyRepository;
import com.edf.datalake.service.dao.TopicRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
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
    private TopicRepository topicRepository;

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    // (Topic, ApiKey) -> Producer
    private Map<String, Map<String, KafkaProducer>> producers;
    private Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private ObjectMapper mapper;
    private static final String SURVEILLANCE_TOPIC_FULLNAME = "topic.surveillance.full.name";
    private static final String METRICS_TOPIC_FULLNAME = "topic.metrics.full.name";

    @PostConstruct
    public void initProducers() {

        logger.info("Initializing producers tree !");

        final String SECURITY_LOGIN       = "java.security.auth.login.config";
        final String SECURITY_KRB5        = "java.security.krb5.conf";
        final String CLIENT_ID            = "client.id";
        final String BOOTSTRAP_SERVERS    = "bootstrap.servers";
        final String ZOOKEEPER            = "zookeeper";
        final String SECURITY_PROTOCOL    = "security.protocol";
        final String TRUSTSTORE_LOCATION  = "ssl.truststore.location";
        final String TRUSTSTORE_PASSWORD  = "ssl.truststore.password";
        final String ACK_STRATEGY         = "acks";
        final String KEY_SERIALIZER       = "key.serializer";
        final String VALUE_SERIALIZER     = "value.serializer";

        producers = new HashMap<>();

        Properties config = new Properties();

        System.setProperty(SECURITY_LOGIN, env.getProperty(SECURITY_LOGIN));
        System.setProperty(SECURITY_KRB5, env.getProperty(SECURITY_KRB5));

        config.put(CLIENT_ID, env.getProperty(CLIENT_ID));
        config.put(BOOTSTRAP_SERVERS, env.getProperty(BOOTSTRAP_SERVERS));
        config.put(ZOOKEEPER, env.getProperty(ZOOKEEPER));
        config.put(SECURITY_PROTOCOL, env.getProperty(SECURITY_PROTOCOL));
        config.put(TRUSTSTORE_LOCATION, env.getProperty(TRUSTSTORE_LOCATION));
        config.put(TRUSTSTORE_PASSWORD, env.getProperty(TRUSTSTORE_PASSWORD));
        config.put(ACK_STRATEGY, env.getProperty(ACK_STRATEGY));
        config.put(KEY_SERIALIZER, env.getProperty(KEY_SERIALIZER));
        config.put(VALUE_SERIALIZER, env.getProperty(VALUE_SERIALIZER));

        List<Topic> topics = topicRepository.findAll();
        List<ApiKey> apiKeys = apiKeyRepository.findAll();

        for(Topic topic : topics) {
            producers.put(topic.getId(), new HashMap<>());
        }

        for(ApiKey apiKey : apiKeys) {
            for(Map.Entry<String, Map<String, KafkaProducer>> entry : producers.entrySet()) {
                entry.getValue().put(apiKey.getId(), new KafkaProducer(config));
            }
        }

        mapper = new ObjectMapper();

        logger.info("Producers tree initialized successfully");
    }

    public Boolean sendMessages(String topic, String apiKey, GenericMessageDTO dto) {
        Boolean isSuccess;

        logger.info("Begin producing to Kafka. Topic : " + topic);

        try {
            KafkaProducer producer = producers.get(topic).get(apiKey);
            JsonNode node;
            ProducerRecord<String, String> msg;

            if(env.getProperty(SURVEILLANCE_TOPIC_FULLNAME).equals(topic)) {
                logger.info("Producing into topic : " + topic);

                SurveillanceDTO surveillanceDTO = (SurveillanceDTO) dto;

                for(SurveillanceEventDTO entry : surveillanceDTO.events) {
                    node = mapper.valueToTree( entry );
                    msg = new ProducerRecord<>(topic, node.toString());
                    producer.send(msg).get();
                }
            }

            if(env.getProperty(METRICS_TOPIC_FULLNAME).equals(topic)) {
                logger.info("Producing into topic : " + topic);

                MetricDTO metricDTO = (MetricDTO) dto;

                for(MetricEventDTO entry : metricDTO.events) {
                    node = mapper.valueToTree( entry );
                    msg = new ProducerRecord<>(topic, node.toString());
                    producer.send(msg).get();
                }
            }

            isSuccess = Boolean.TRUE;

        } catch (Exception e) {
            logger.error( e.toString() );
            isSuccess = Boolean.FALSE;
        }

        return isSuccess;
    }

    @PreDestroy
    public void cleanup() {
        logger.info("Destroying producers tree !");

        for(Map.Entry<String, Map<String, KafkaProducer>> entry : producers.entrySet()) {
            for(Map.Entry<String, KafkaProducer> element : entry.getValue().entrySet()) {
                element.getValue().close();
            }
        }
    }

}



