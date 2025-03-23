package task.schedule.service;

import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.entity.User;

public interface UserService {

    UserResponseDto saveUser(UserRequestDto requestDto);
    UserResponseDto findUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto requestDto);
    void deleteUser(Long id);
}
