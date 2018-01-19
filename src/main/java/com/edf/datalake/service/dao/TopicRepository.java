package com.edf.datalake.service.dao;

import com.edf.datalake.model.entity.Topic;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable
public interface TopicRepository extends JpaRepository<Topic, String> {

}
