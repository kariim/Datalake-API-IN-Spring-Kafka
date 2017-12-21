package com.edf.datalake.controller;

import com.edf.datalake.model.dto.Message;
import com.edf.datalake.service.AccessPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-in")
public class ApiInController {

    @Autowired
    private AccessPointService accessPointService;

    private Logger logger = LoggerFactory.getLogger(ApiInController.class);


    @GetMapping(path = "/messages/{topic}/{apiKey}", produces = "application/json")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable(value = "topic") String topic, @PathVariable(value = "apiKey") String apiKey) {
        Boolean granted = accessPointService.checkPrerequisites(topic, apiKey);
        List<Message> results = null;

        if(granted) {
            results = accessPointService.getCurrentMessages(topic, apiKey);
            return new ResponseEntity<>(results, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(results, HttpStatus.BAD_REQUEST);
        }
    }

}
