package com.edf.datalake.model;

import javax.persistence.*;

@Entity
@Table(name = "api_keys")
@EntityListeners(ApiKey.class)
public class ApiKey {

    @Id
    @Column(name = "api_key")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other) ||  this.id.equals( ((ApiKey) other).id  );
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
