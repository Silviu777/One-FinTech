package com.fintech.mapper;

import com.fintech.dto.UserDto;
import com.fintech.model.User;


public class UserMapper {

    public static UserDto mapToDto(User user) {

        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUsername())
                .address(user.getAddress())
                .city(user.getCity())
                .country(user.getCountry())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .emailAddress(user.getEmailAddress())
                .build();

    }

    public static User mapToEntity(UserDto userDTO) {

        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUserName())
                .address(userDTO.getAddress())
                .city(userDTO.getCity())
                .country(userDTO.getCountry())
                .phoneNumber(userDTO.getPhoneNumber())
                .birthDate(userDTO.getBirthDate())
                .emailAddress(userDTO.getEmailAddress())
                .build();

    }
}