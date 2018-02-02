package com.edf.datalake.service;

import com.edf.datalake.model.dto.GenericMessageDTO;
import com.edf.datalake.model.entity.ApiKey;
import com.edf.datalake.model.entity.Topic;
import com.edf.datalake.model.dto.RequestDTO;
import com.edf.datalake.service.dao.ApiKeyRepository;
import com.edf.datalake.service.dao.TopicRepository;
import com.edf.datalake.service.kafka.ProducerService;
import com.edf.datalake.utils.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AccessPointService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private Environment env;

    private static final String REQUEST_SIZE = "max.request.length";
    private static final String SURVEILLANCE = "topic.surveillance";
    private static final String METRICS = "topic.metrics";
    private static final String TOPIC_PREFIX = "topic.prefix";

    private Logger logger = LoggerFactory.getLogger(AccessPointService.class);

    public Boolean checkRequestLength(Long length) {
        return ( length > Long.valueOf(env.getProperty(REQUEST_SIZE)) ) ?
                Boolean.FALSE : Boolean.TRUE;
    }

    public GenericMessageDTO checkJsonFormat(RequestDTO dto, String type) {
        GenericMessageDTO result = null;

        if(env.getProperty(SURVEILLANCE).equals(type)) {
            result = Transformers.requestToSurveillance(dto);
        }

        if(env.getProperty(METRICS).equals(type)) {
            result = Transformers.requestToMetric(dto);
        }

        return result;
    }

    public Boolean checkAccessRights(String apiKey, String topic) {
        ApiKey apiKeyEntity = apiKeyRepository.findOne(apiKey);
        Topic topicEntity = topicRepository.findOne(env.getProperty(TOPIC_PREFIX) + topic);

        return apiKeyEntity != null && topicEntity != null;
    }

    public Boolean produceToKafka(String topic, String apiKey, GenericMessageDTO dto) {
        return producerService.storeMessages(env.getProperty(TOPIC_PREFIX) + topic, apiKey, dto);
    }

    public void shutdown() {
        producerService.shutdown();
    }
}
