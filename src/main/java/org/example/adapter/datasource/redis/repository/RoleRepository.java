package org.example.adapter.datasource.redis.repository;

import org.example.adapter.datasource.redis.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleModel, String> {
}
