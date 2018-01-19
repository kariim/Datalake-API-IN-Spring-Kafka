package com.edf.datalake.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "topics")
@EntityListeners(Topic.class)
public class Topic {

    @Id
    @Column(name = "topic")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other) ||  this.id.equals( ((Topic) other).id  );
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
