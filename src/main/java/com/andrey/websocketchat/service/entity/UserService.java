package com.andrey.websocketchat.service.entity;

import com.andrey.websocketchat.entity.User;
import com.andrey.websocketchat.exception.UserAlreadyExistsException;
import com.andrey.websocketchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }

        return save(user);
    }

    public User findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public boolean existsByName(String name) {
        return repository.existsByUsername(name);
    }
}
