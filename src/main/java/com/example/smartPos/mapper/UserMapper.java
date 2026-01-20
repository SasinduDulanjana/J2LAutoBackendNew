package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.repositories.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", ignore = true) // Skip mapping roles
    User toUser(UserRequest userRequest);

    @Mapping(target = "roles", ignore = true) // Skip mapping roles
    UserRequest toUserRequest(User user);
}
