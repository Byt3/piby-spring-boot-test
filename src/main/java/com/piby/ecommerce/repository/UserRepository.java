package com.piby.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.piby.ecommerce.entities.User;

/**
 * Created by marco on 30/11/2016.
 */
public interface UserRepository extends CrudRepository<User, Long> {

        User findByUsername(String username);

}
