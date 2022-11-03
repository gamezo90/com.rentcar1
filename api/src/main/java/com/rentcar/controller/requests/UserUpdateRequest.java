package com.rentcar.controller.requests;

import lombok.Data;

@Data
public class UserUpdateRequest extends UserCreateRequest {

    private Long id;
}
