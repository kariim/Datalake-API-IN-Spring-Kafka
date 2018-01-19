package com.edf.datalake.model.dto;

import java.util.List;

public class RequestDTO {

    public String object_type;
    public String heartbeat;
    public List<EventDTO> events;

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append( super.toString() );
        buffer.append("\n");
        buffer.append("Object Type : " + object_type);
        buffer.append("\n");
        buffer.append("Heartbeat : " + heartbeat);
        buffer.append("\n");

        for(int i=0; i<events.size(); i++) {
            buffer.append("Metric " + i + " : " + events.get(i).toString());
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
