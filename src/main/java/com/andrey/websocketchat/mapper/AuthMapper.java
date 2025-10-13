package com.andrey.websocketchat.mapper;

import com.andrey.websocketchat.config.MapstructConfig;
import com.andrey.websocketchat.dto.auth.AccessTokenRs;
import com.andrey.websocketchat.dto.auth.SignInRs;
import com.andrey.websocketchat.dto.auth.SignUpRs;
import com.andrey.websocketchat.model.AccessToken;
import com.andrey.websocketchat.model.UserPrincipal;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface AuthMapper {
    SignUpRs mapToSignUpRs(UserPrincipal user, String accessToken);

    SignInRs mapToSignInRs(UserPrincipal user, String accessToken);

    AccessTokenRs map(AccessToken accessToken);
}
