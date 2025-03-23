package task.schedule.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.entity.User;
import task.schedule.repository.UserRepository;

@Service
public class UserServiceLv3 implements UserService{

    private final UserRepository userRepository;

    public UserServiceLv3(UserRepository userRepository) {
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."));

        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
