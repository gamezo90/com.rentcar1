package com.noirix.controller.converters;

import com.noirix.controller.requests.UserChangeRequest;
import com.noirix.domain.HibernateUser;
import com.noirix.repository.UserSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserChangeConverter extends UserBaseConverter<UserChangeRequest, HibernateUser> {

    private final UserSpringDataRepository repository;

    @Override
    public HibernateUser convert(UserChangeRequest source) {

        Optional<HibernateUser> user = repository.findById(source.getId());
        return doConvert(user.get(), source);
    }
}
