package com.edf.datalake.service;

import com.edf.datalake.model.entity.ApiKey;
import com.edf.datalake.model.entity.KafkaTopic;
import com.edf.datalake.model.dto.Message;
import com.edf.datalake.service.dao.ApiKeyRepository;
import com.edf.datalake.service.kafka.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccessPointService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    private ConsumerService consumer;

    private Logger logger = LoggerFactory.getLogger(AccessPointService.class);

    public List<Message> getCurrentMessages(String topic, String apiKey) {
        logger.info("Getting messages for topic : " + topic);
        consumer.getMessages(topic, apiKey);

        return null;
    }

    public Boolean checkPrerequisites(String topic, String apiKey) {
        KafkaTopic temporaryTopic = new KafkaTopic(topic);
        ApiKey entity = apiKeyRepository.findOne(apiKey);

        if(entity == null || entity.getTopics() == null || entity.getTopics().isEmpty())
            return Boolean.FALSE;

        return (entity.getTopics().contains(temporaryTopic));
    }

}
