package com.edf.datalake.model.dto;

public class SurveillanceEventDTO {
    public Long timestamp;
    public String hostname;
    public String criticite;
    public String discriminant;
    public String module;
    public String message;

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

        return buffer.toString();
    }

}
