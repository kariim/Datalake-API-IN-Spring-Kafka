package com.edf.datalake.model.dto;

import java.util.List;

public class SurveillanceDTO extends GenericMessageDTO {

    public List<SurveillanceEventDTO> events;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append(super.toString());

        for(SurveillanceEventDTO dto : events) {
            buffer.append(dto.toString());
        }

        return buffer.toString();
    }
}
