package com.edf.datalake.model.dto;

public class GenericMessageDTO {

    public String object_type;
    public String heartbeat;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("object_type : " + object_type);
        buffer.append("\n");
        buffer.append("heartbeat : " + heartbeat);
        buffer.append("\n");

        return buffer.toString();
    }
}
