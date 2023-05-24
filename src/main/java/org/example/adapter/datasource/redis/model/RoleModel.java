package org.example.adapter.datasource.redis.model;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("roles")
public class RoleModel {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
