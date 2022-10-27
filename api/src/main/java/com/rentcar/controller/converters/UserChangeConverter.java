package com.rentcar.controller.converters;

import com.rentcar.controller.requests.UserChangeRequest;
import com.rentcar.domain.User;
import com.rentcar.repository.UserSpringDataRepository;
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
