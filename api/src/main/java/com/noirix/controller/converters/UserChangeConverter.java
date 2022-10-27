package com.noirix.controller.converters;

import com.noirix.controller.requests.UserChangeRequest;
import com.noirix.domain.User;
import com.noirix.repository.UserSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserChangeConverter extends UserBaseConverter<UserChangeRequest, User> {

    private final UserSpringDataRepository repository;

    @Override
    public User convert(UserChangeRequest source) {

        Optional<User> user = repository.findById(source.getId());
        return doConvert(user.get(), source);
    }
}
