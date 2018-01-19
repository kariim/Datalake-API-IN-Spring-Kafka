package com.edf.datalake.model.dto;

import java.util.List;

public class MetricEventDTO {

    public Long timestamp;
    public String hostname;
    public String instance;
    public List<MetricValueDTO> metrics_array;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append("Timestamp : " + timestamp);
        buffer.append("\n");
        buffer.append("Hostname : " + hostname);
        buffer.append("\n");
        buffer.append("instance : " + instance);

        if(metrics_array != null) {
            for(MetricValueDTO dto : metrics_array) {
                buffer.append( dto.toString() );
            }
        }

        return buffer.toString();
    }

}
