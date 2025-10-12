package com.andrey.websocketchat.service.entity.impl;

import com.andrey.websocketchat.exception.EntityAlreadyExistsException;
import com.andrey.websocketchat.model.User;
import com.andrey.websocketchat.repository.UserRepository;
import com.andrey.websocketchat.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (existsByName(user.getUsername())) {
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        return save(user);
    }

    public User findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("user with name %s not found", username)));
    }

    public boolean existsByName(String name) {
        return repository.existsByUsername(name);
    }
}
