package com.terratech.api.services.implementaions;

import com.terratech.api.dto.UserRequest;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User create(UserRequest user) {

        User userToSave = user.toUser();
        this.userAlreadyExists(userToSave.getEmail());

        return this.repository.save(userToSave);
    }

    private void userAlreadyExists(String email) {
        this.repository.findByEmail(email)
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });
    }
}
