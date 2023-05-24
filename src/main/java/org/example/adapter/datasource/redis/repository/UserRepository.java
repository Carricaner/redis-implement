package org.example.adapter.datasource.redis.repository;

import org.example.adapter.datasource.redis.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
}
