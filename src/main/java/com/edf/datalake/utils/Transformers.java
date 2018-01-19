package com.edf.datalake.utils;

import com.edf.datalake.model.dto.*;

import java.util.ArrayList;
import java.util.List;

public class Transformers {

    public static SurveillanceDTO requestToSurveillance(RequestDTO dto) {

        if(dto.object_type == null || dto.heartbeat == null || dto.events == null ||
                "".equals(dto.object_type) || "".equals(dto.heartbeat) || dto.events.isEmpty()) {

            return null;
        }

        SurveillanceDTO result = new SurveillanceDTO();

        result.object_type = dto.object_type;
        result.heartbeat = dto.heartbeat;
        result.events = eventToSurveillanceEvent(dto.events);

        return result;
    }

    public static MetricDTO requestToMetric(RequestDTO dto) {

        if(dto.object_type == null || dto.heartbeat == null || dto.events == null ||
                "".equals(dto.object_type) || "".equals(dto.heartbeat) || dto.events.isEmpty()) {

            return null;
        }

        MetricDTO result = new MetricDTO();

        result.object_type = dto.object_type;
        result.heartbeat = dto.heartbeat;
        result.events = eventToMetricEvent(dto.events);

        return result;
    }

    public static List<SurveillanceEventDTO> eventToSurveillanceEvent(
            List<EventDTO> events) {

        List<SurveillanceEventDTO> result = new ArrayList<>();

        for(EventDTO entry : events) {
            SurveillanceEventDTO e = new SurveillanceEventDTO();
            e.criticite = entry.criticite;
            e.discriminant = entry.discriminant;
            e.hostname = entry.hostname;
            e.message = entry.message;
            e.module = entry.module;
            e.timestamp = entry.timestamp;

            result.add(e);
        }

        return result;
    }

    public static List<MetricEventDTO> eventToMetricEvent(List<EventDTO> events) {

        List<MetricEventDTO> result = new ArrayList<>();

        for(EventDTO entry : events) {
            MetricEventDTO e = new MetricEventDTO();
            e.hostname = entry.hostname;
            e.instance = entry.instance;
            e.timestamp = entry.timestamp;
            e.metrics_array = entry.metrics_array;

            result.add(e);
        }

        return result;
    }

}
