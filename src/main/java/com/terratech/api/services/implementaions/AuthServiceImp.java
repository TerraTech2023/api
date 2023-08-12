package com.terratech.api.services.implementaions;

import com.terratech.api.dto.Login;
import com.terratech.api.model.Abstract.Person;
import com.terratech.api.model.Collector;
import com.terratech.api.model.User;
import com.terratech.api.repositories.CollectorRepository;
import com.terratech.api.repositories.UserRepository;
import com.terratech.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.terratech.api.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final CollectorRepository collectorRepository;

    @Override
    public String register(Person person) {
        Person saved = save(person);

        return "token do usuario";
    }

    @Override
    public String login(Login login) {
        return "token do usuario";
    }


    private Person save(Person person) {
        Person user;
        if (person.getRole().equals(USER))
            user = saveUser(person);
        else
            user = saveCollector(person);
        return user;
    }

    private User saveUser(Person person) {
        return userRepository.save((User) person);
    }

    private Collector saveCollector(Person person) {
        return collectorRepository.save((Collector) person);
    }

}
