package com.andrey.websocketchat.service.entity;

import com.andrey.websocketchat.model.User;

import java.util.UUID;

public interface UserService {
    User save(User user);

    User create(User user);

    User findById(UUID id);

    User getByUsername(String username);

    boolean existsByName(String name);
}
