package com.terratech.api.services;

import com.terratech.api.dto.UserRequest;
import com.terratech.api.model.User;

public interface UserService {
    User findById(Long id);

    User create(UserRequest user);

    User update(Long id, UserRequest request);

    void delete(Long id);
}
