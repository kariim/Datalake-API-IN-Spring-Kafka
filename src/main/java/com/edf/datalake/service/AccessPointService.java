package com.edf.datalake.service;

import com.edf.datalake.model.entity.ApiKey;
import com.edf.datalake.model.entity.KafkaTopic;
import com.edf.datalake.model.dto.Message;
import com.edf.datalake.service.dao.ApiKeyRepository;
import com.edf.datalake.service.dao.TopicRepository;
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
    private TopicRepository topicRepository;

    private Logger logger = LoggerFactory.getLogger(AccessPointService.class);

    public List<Message> getCurrentMessages(String topic, String apiKey) {
        // TODO Implement Kafka Consumer service here !

        return null;
    }

    public Boolean checkPrerequisites(String topic, String apiKey) {
        KafkaTopic topicEntity = topicRepository.findOne(topic);
        ApiKey apiKeyEntity = apiKeyRepository.findOne(apiKey);

        return topicEntity != null && apiKeyEntity != null;
    }

}
