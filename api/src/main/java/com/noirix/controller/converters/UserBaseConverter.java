package com.noirix.controller.converters;

import com.noirix.controller.requests.UserCreateRequest;
import com.noirix.domain.User;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

public abstract class UserBaseConverter<S, T> implements Converter<S, T> {

    public User doConvert(User userForUpdate, UserCreateRequest request) {

        userForUpdate.setUserName(request.getUserName());
        userForUpdate.setSurname(request.getSurname());
        userForUpdate.setBirthday(request.getBirth());

        /*System fields filling*/
        userForUpdate.setModificationDate(new Timestamp(new Date().getTime()));
        userForUpdate.setIsDeleted(false);
        
        return userForUpdate;
    }

}
