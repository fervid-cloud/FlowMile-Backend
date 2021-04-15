package com.mss.polyflow.shared.dto;

import com.mss.polyflow.shared.dto.response.UserDetailDto;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.security.authentication.CurrentUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public final class UserMapper {

    public static UserDetailDto toUserDetailDto(User user) {
        log.info("User data passed is {}", user);
        if(user == null) {
            return null;
        }
        UserDetailDto userDetailDto = new UserDetailDto();
        BeanUtils.copyProperties(user, userDetailDto);
        log.info("Updated userDetail is {}", userDetailDto);
        return userDetailDto;
    }

    public static CurrentUserDetail toCurrentUserDetail(User user) {
        log.info("user data passed is {}", user);
        if(user == null) {
            return null;
        }
        CurrentUserDetail currentUserDetail = new CurrentUserDetail();
        BeanUtils.copyProperties(user, currentUserDetail);
        log.info("Updated CurrentUserDetail is {}", currentUserDetail);
        return currentUserDetail;
    }
}
