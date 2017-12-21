package com.edf.datalake.service.dao;

import com.edf.datalake.model.entity.KafkaTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<KafkaTopic, String> {

}
