package task.schedule.service;

import org.springframework.stereotype.Service;
import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.dto.UserUpdateRequestDto;
import task.schedule.entity.User;
import task.schedule.exception.CustomException;
import task.schedule.exception.ExceptionCode;
import task.schedule.repository.ScheduleRepository;
import task.schedule.repository.UserRepository;

/**
 * 사용자 관련 내부 로직
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public UserServiceImpl(UserRepository userRepository, ScheduleRepository scheduleRepository) {
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 사용자 등록
     *
     * @param requestDto 등록할 사용자 정보
     * @return 등록된 사용자 정보가 담긴 UserResponseDto
     */
    @Override
    public UserResponseDto saveUser(UserRequestDto requestDto) {
        User user = new User(requestDto.getName(), requestDto.getEmail());
        return new UserResponseDto(userRepository.saveUser(user));
    }

    /**
     * 사용자 조회
     *
     * @param id 사용자 식별자
     * @return 조회된 사용자 정보가 담긴 UserResponseDto
     *         - 조회 실패 시 NOT_FOUND_USER 응답
     */
    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        return new UserResponseDto(user);
    }

    /**
     * 사용자 정보 변경
     *
     * @param id 사용자 식별자
     * @param requestDto 변경할 사용자 정보
     * @return 성공 시 변경된 사용자 재 조회한 결과 전달
     *         - 변경할 사용자가 존재하지 않을 경우 NOT_FOUND_USER 응답
     *         - 변경된 내용이 없을 경우 NO_CHANGES 응답
     *         - 변경된 row가 없을 경우 UPDATE_FAILED 응답
     *         - 변경 성공 후 재 조회에 실패할 경우 RELOAD_FAILED 응답
     */
    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        String beforeName = user.getName();
        String beforeEmail = user.getEmail();

        if (requestDto.getName() != null) user.updateName(requestDto.getName());
        if (requestDto.getEmail() != null) user.updateEmail(requestDto.getEmail());

        if (beforeName.equals(user.getName()) && beforeEmail.equals(user.getEmail())) {
            throw new CustomException(ExceptionCode.NO_CHANGES);
        }

        int result = userRepository.updateUser(id, user.getName(), user.getEmail());

        if (result == 0) {
            throw new CustomException(ExceptionCode.UPDATE_FAILED);
        }

        User updated = userRepository.findUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.RELOAD_FAILED));

        return new UserResponseDto(updated);
    }

    /**
     * 사용자 삭제
     *  - 삭제할 사용자가 존재하지 않을 경우 NOT_FOUND_USER 응답
     *  - 변경된 row가 없을 경우 DELETE_FAILED 응답
     *  - 해당 사용자와 연관된 일정도 모두 삭제
     *  - 삭제 성공 시 반환 값 없음
     *
     * @param id 사용자 식별자
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.findUserById(id)
            .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        scheduleRepository.deleteSchedulesByUserId(id);

        int result = userRepository.deleteUser(id);

        if (result == 0) {
            throw new CustomException(ExceptionCode.DELETE_FAILED);
        }
    }
}
