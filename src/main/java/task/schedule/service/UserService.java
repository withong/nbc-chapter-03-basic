package task.schedule.service;

import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.dto.UserUpdateRequestDto;
import task.schedule.entity.User;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto requestDto);
    UserResponseDto findUserById(Long id);
    UserResponseDto updateUser(Long id, UserUpdateRequestDto requestDto);
    void deleteUser(Long id);
}
