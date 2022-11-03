package com.rentcar.controller.converters;

import com.rentcar.controller.requests.UserUpdateRequest;
import com.rentcar.domain.User;
import com.rentcar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUpdateConverter extends UserBaseConverter<UserUpdateRequest, User> {

    private final UserRepository repository;

    @Override
    public User convert(UserUpdateRequest source) {

        Optional<User> user = repository.findById(source.getId());
        return doUpdate(user.get(), source);
    }
}
