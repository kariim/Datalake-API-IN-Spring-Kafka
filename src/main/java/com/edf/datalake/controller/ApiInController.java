package com.edf.datalake.controller;

import com.edf.datalake.model.dto.MessageDTO;
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
                                          @RequestBody MessageDTO message) {

        if( ! accessPointService.checkRequestLength(length) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if( ! accessPointService.checkJsonFormat(message) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if( ! accessPointService.checkAccessRights(apiKey, message.object_type) ) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

