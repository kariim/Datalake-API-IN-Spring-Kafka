package com.edf.datalake.controller;

import com.edf.datalake.model.dto.GenericMessageDTO;
import com.edf.datalake.model.dto.RequestDTO;
import com.edf.datalake.service.AccessPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-in")
public class ApiInController {

    @Autowired
    private AccessPointService accessPointService;

    private Logger logger = LoggerFactory.getLogger(ApiInController.class);


    @PostMapping(path = "/post-data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postJsonContent(@RequestHeader("Authorization") String apiKey,
                                          @RequestHeader("Content-Length") Long length,
                                          @RequestBody RequestDTO request) {

        // Transform the json request format and transform it to either Metric or Surveillance
        GenericMessageDTO message = accessPointService.checkJsonFormat(request, request.object_type);

        if( ! accessPointService.checkRequestLength(length) ) {
            logger.error("Request length exceeds the authorized limit : 1MB");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if( message == null ) {
            logger.error("Json Request doesn't match the SLA criteria !");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if( ! accessPointService.checkAccessRights(apiKey, request.object_type) ) {
            logger.error("The API KEY or Object type is incorrect !");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if( accessPointService.produceToKafka(message.object_type, apiKey, message) ) {
            logger.info("Granted, and the message is successfully pushed to Kafka");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}

