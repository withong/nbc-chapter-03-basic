package task.schedule.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.entity.User;
import task.schedule.exception.CustomException;
import task.schedule.exception.ExceptionCode;
import task.schedule.repository.UserRepository;

@Service
public class UserServiceLv5 implements UserService{

    private final UserRepository userRepository;

    public UserServiceLv5(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getName(), requestDto.getEmail());
        return new UserResponseDto(userRepository.saveUser(user));
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        if (requestDto.getName() != null) user.updateName(requestDto.getName());
        if (requestDto.getEmail() != null) user.updateEmail(requestDto.getEmail());

        int result = userRepository.updateUser(id, user.getName(), user.getEmail());

        if (result == 0) {
            throw new CustomException(ExceptionCode.UPDATE_FAILED);
        }

        User updated = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.RELOAD_FAILED));

        return new UserResponseDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findUserById(id)
            .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        int result = userRepository.deleteUser(id);

        if (result == 0) {
            throw new CustomException(ExceptionCode.DELETE_FAILED);
        }
    }
}
