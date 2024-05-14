package org.bilko.educationalprogram.mapper;

import org.bilko.educationalprogram.config.MapperConfig;
import org.bilko.educationalprogram.dto.user.UserResponseDto;
import org.bilko.educationalprogram.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = CourseMapper.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
}
