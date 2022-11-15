package com.rentcar.controller.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class UserResponse {

    private long id;

    private String userName;

    private String surname;

    private boolean isBanned;

    private boolean isDeleted;

    private String region;

    private Timestamp birthday;

    private String gender;

    private String userLogin;

    private String userPassword;

    private String userEmail;

    private Set<RoleResponse> userRoles;

}




