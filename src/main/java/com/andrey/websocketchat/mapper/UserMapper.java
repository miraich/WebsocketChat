package com.andrey.websocketchat.mapper;

import com.andrey.websocketchat.config.MapstructConfig;
import com.andrey.websocketchat.dto.auth.SignUpRq;
import com.andrey.websocketchat.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {
    User map(SignUpRq signUpRq);
}
