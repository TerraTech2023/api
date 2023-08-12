package com.terratech.api.services;

import com.terratech.api.dto.Login;
import com.terratech.api.model.Abstract.Person;

public interface AuthService {
    String register(Person person);
    String login(Login login);
}
