package com.edf.datalake.utils;

import com.edf.datalake.model.dto.ApiKeyDTO;
import com.edf.datalake.model.dto.KafkaTopicDTO;
import com.edf.datalake.model.entity.ApiKey;
import com.edf.datalake.model.entity.KafkaTopic;

import java.util.ArrayList;

public class Transformations {

    public static KafkaTopicDTO fromEntityToDTO(KafkaTopic entity) {
        KafkaTopicDTO dto = new KafkaTopicDTO();
        dto.id = entity.getId();

        return dto;
    }

    public static ApiKeyDTO fromEntityToDTO(ApiKey entity) {
        ApiKeyDTO dto = new ApiKeyDTO();

        dto.id = entity.getId();
        dto.topics = new ArrayList<>();

        for(KafkaTopic topic : entity.getTopics()) {
            dto.topics.add( fromEntityToDTO(topic) );
        }

        return dto;
    }

}
