package com.edf.datalake.model.dto;

import java.util.List;

public class MetricDTO extends GenericMessageDTO {

    public List<MetricEventDTO> events;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append(super.toString());

        for(MetricEventDTO dto : events) {
            buffer.append(dto.toString());
        }

        return buffer.toString();
    }
}
