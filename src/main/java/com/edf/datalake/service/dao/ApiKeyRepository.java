package com.edf.datalake.service.dao;

import com.edf.datalake.model.entity.ApiKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable
public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {

}
