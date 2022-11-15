package com.rentcar.controller.mappers;

import com.rentcar.controller.requests.UserCreateRequest;
import com.rentcar.controller.response.UserResponse;
import com.rentcar.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(source = "roles", target = "userRoles")
    UserResponse toResponse(User user);

    @Mapping(source = "login",target = "credentials.Login")
    @Mapping(source = "password",target = "credentials.Password")
    @Mapping(source = "email",target = "credentials.email")
    User convertCreateRequest(UserCreateRequest userCreateRequest);
}
