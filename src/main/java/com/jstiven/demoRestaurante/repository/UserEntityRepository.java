package com.jstiven.demoRestaurante.repository;

import com.jstiven.demoRestaurante.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity,Long> {

    public Optional<UserEntity> findUserEntityByUsername(String username);

}
