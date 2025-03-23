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
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."));

        if (requestDto.getName() != null) user.updateName(requestDto.getEmail());
        if (requestDto.getEmail() != null) user.updateEmail(requestDto.getEmail());

        int result = userRepository.updateUser(id, user.getName(), user.getEmail());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 정보 수정에 실패했습니다.");
        }

        User updated = userRepository.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "데이터를 불러오는 데 실패했습니다."));

        return new UserResponseDto(updated);
    }

    @Override
    public void deleteUser(Long id) {

    }
}
