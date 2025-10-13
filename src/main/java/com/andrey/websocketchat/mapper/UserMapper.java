package com.andrey.websocketchat.mapper;

import com.andrey.websocketchat.config.MapstructConfig;
import com.andrey.websocketchat.dto.auth.SignInRq;
import com.andrey.websocketchat.dto.auth.SignUpRq;
import com.andrey.websocketchat.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User map(SignUpRq signUpRq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User map(SignInRq signUpRq);
}
