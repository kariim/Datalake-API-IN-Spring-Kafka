package com.edf.datalake.model.dto;

import java.util.List;

public class EventDTO {

    public Long timestamp;
    public String hostname;

    /* Surveillance attributes */
    public String criticite;
    public String discriminant;
    public String module;
    public String message;

    /* Metrics attributes */
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
        buffer.append("criticite : " + criticite);
        buffer.append("\n");
        buffer.append("discriminant : " + discriminant);
        buffer.append("\n");
        buffer.append("module : " + module);
        buffer.append("\n");
        buffer.append("message : " + message);
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
