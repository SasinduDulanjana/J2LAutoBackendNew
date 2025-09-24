package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.repositories.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T13:46:01+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setId( userRequest.getId() );
        user.setUsername( userRequest.getUsername() );
        user.setPassword( userRequest.getPassword() );
        user.setEmail( userRequest.getEmail() );
        user.setStatus( userRequest.getStatus() );

        return user;
    }

    @Override
    public UserRequest toUserRequest(User user) {
        if ( user == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        userRequest.setId( user.getId() );
        userRequest.setUsername( user.getUsername() );
        userRequest.setEmail( user.getEmail() );
        userRequest.setStatus( user.getStatus() );
        userRequest.setPassword( user.getPassword() );

        return userRequest;
    }
}
