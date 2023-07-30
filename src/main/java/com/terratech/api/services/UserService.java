package com.terratech.api.services;

import com.terratech.api.dto.user.UserRequest;
import com.terratech.api.dto.user.UserResponse;
import com.terratech.api.model.User;

public interface UserService {
    UserResponse findById(Long id);


    void update(Long id, UserRequest request);

    void delete(Long id);
}
