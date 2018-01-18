package com.edf.datalake.model.dto;

public class MetricValueDTO {
    public String metric_name;
    public String metric_value;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n");
        buffer.append("Metric_name : " + metric_name);
        buffer.append("\n");
        buffer.append("Metric_value : " + metric_value);
        buffer.append("\n");

        return buffer.toString();
    }
}
