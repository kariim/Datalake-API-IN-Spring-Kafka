package com.edf.datalake.service;

import com.edf.datalake.model.ApiKey;
import com.edf.datalake.model.Topic;
import com.edf.datalake.model.dto.MessageDTO;
import com.edf.datalake.service.dao.ApiKeyRepository;
import com.edf.datalake.service.dao.TopicRepository;
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
    private Environment env;

    private static final String REQUEST_SIZE = "max.request.length";
    private Logger logger = LoggerFactory.getLogger(AccessPointService.class);

    public Boolean checkRequestLength(Long length) {
        return ( length > Long.valueOf(env.getProperty(REQUEST_SIZE)) ) ?
                Boolean.FALSE : Boolean.TRUE;
    }

    public Boolean checkJsonFormat(MessageDTO dto) {
        return Boolean.TRUE;
    }

    public Boolean checkAccessRights(String apiKey, String topic) {
        ApiKey apiKeyEntity = apiKeyRepository.findOne(apiKey);
        Topic topicEntity = topicRepository.findOne(topic);

        return apiKeyEntity != null && topicEntity != null;
    }




}
