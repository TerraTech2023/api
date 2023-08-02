package com.terratech.api.services.implementaions;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.exception.ConflictException;
import com.terratech.api.exception.NotFoundException;
import com.terratech.api.model.User;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse findById(Long id) {
        User user = this.userAlreadyExists(id);
        return new UserResponse(user);
    }


    @Override
    public void update(Long id, UserRequest request) {

        User user = this.userAlreadyExists(id);
        this.emailAlreadyExists(request.email());

        this.modelMapper.map(request.toUser(), user);

        this.repository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = this.userAlreadyExists(id);
        this.repository.delete(user);
    }

    private void emailAlreadyExists(String email) {
        boolean emailUsed = this.repository.findByEmail(email).isPresent();

        if (emailUsed)
            throw new ConflictException("Email already used");
    }

    private User userAlreadyExists(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
