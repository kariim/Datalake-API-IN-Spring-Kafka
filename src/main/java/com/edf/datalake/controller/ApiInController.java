package com.edf.datalake.controller;

import com.edf.datalake.service.AccessPointService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-out")
public class ApiInController {

    @Autowired
    private AccessPointService accessPointService;

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(ApiInController.class);
    private static final String TOPIC_PREFIX = "topic.prefix";

    @GetMapping(path = "/messages/{topic}", produces = "application/json")
    public ResponseEntity<List<JSONObject>> getAllMessages(@PathVariable(value = "topic") String topic, @RequestHeader("Authorization") String apiKey) {

        String fullTopic = env.getProperty(TOPIC_PREFIX) + topic;
        Boolean granted = accessPointService.checkPrerequisites(fullTopic, apiKey);
        List<JSONObject> results = null;

        if(granted) {

            logger.info("GRANTED");

            results = accessPointService.getCurrentMessages(fullTopic);
            return new ResponseEntity<>(results, HttpStatus.OK);

        } else {
            logger.info("NOT GRANTED");
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }
    }

}

