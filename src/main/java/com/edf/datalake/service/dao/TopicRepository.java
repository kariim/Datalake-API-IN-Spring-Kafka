package com.edf.datalake.service.dao;

import com.edf.datalake.model.KafkaTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<KafkaTopic, String> {

}
