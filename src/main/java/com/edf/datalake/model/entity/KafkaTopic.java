package com.edf.datalake.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "authorized_topics")
@EntityListeners(KafkaTopic.class)
public class KafkaTopic {

    @Id
    @Column(name = "url_suffix")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
