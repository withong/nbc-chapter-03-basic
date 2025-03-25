package task.schedule.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import task.schedule.dto.PageResponseDto;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.entity.Schedule;
import task.schedule.exception.CustomException;
import task.schedule.exception.ExceptionCode;
import task.schedule.repository.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 일정 관련 내부 로직
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public ScheduleServiceImpl(
            ScheduleRepository scheduleRepository,
            UserService userService
    ) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    /**
     * 일정 등록
     *
     * @param requestDto 등록할 일정 정보
     *        - 전달받은 사용자 ID로 해당 사용자 조회
     *        - 사용자가 존재하면 해당 사용자 테이블의 사용자 식별자 전달
     * @return 등록된 일정 정보와 사용자 이름이 포함된 ScheduleResponseDto
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.findUserById(requestDto.getUserId());

        Schedule schedule = new Schedule(
                userResponseDto.getId(), // 사용자 ID 참조
                requestDto.getDate(),
                requestDto.getContent(),
                requestDto.getPassword()
        );

        Schedule saved = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(saved, userResponseDto.getName());
    }

    /**
     * 일정 목록 조회
     *
     * @param userId 사용자 식별자
     * @param updatedDate 수정일
     * @param page 페이지 번호
     * @param size 페이지 당 항목 개수
     * @return 조회된 일정 목록이 담긴 ScheduleResponseDto 리스트
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesWithUserByUserId(
            Long userId, LocalDate updatedDate, Integer page, Integer size
    ) {
        UserResponseDto userResponseDto = userService.findUserById(userId);

        int offset = (page-1) * size;
        PageResponseDto pageResponseDto = new PageResponseDto(page, size, offset);

        LocalDateTime start = null;
        LocalDateTime end = null;

        if (updatedDate != null) {
            start = updatedDate.atStartOfDay();
            end = updatedDate.plusDays(1).atStartOfDay();
        }

        return scheduleRepository.findSchedulesWithUserByUserId(
                userResponseDto.getId(),
                start,
                end,
                pageResponseDto.getSize(),
                pageResponseDto.getOffset()
        );
    }

    /**
     * 일정 단건 조회 (사용자 이름 포함)
     *
     * @param id 일정 식별자
     * @return 조회된 일정과 사용자 이름이 담긴 ScheduleResponseDto
     *         - 조회 실패 시 NOT_FOUND_SCHEDULE 응답
     */
    @Override
    public ScheduleResponseDto findScheduleWithUserById(Long id) {
        return scheduleRepository.findScheduleWithUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));
    }

    /**
     * 일정 변경
     *
     * @param id 일정 식별자
     * @param requestDto 변경할 일정 정보
     * @return 성공 시 변경된 일정 재 조회한 결과 전달 (사용자 이름 포함)
     *         - 변경할 일정이 존재하지 않을 경우 NOT_FOUND_SCHEDULE 응답
     *         - 비밀번호가 일치하지 않을 경우 INVALID_PASSWORD 응답
     *         - 변경된 내용이 없을 경우 NO_CHANGES 응답
     *         - 변경된 row가 없을 경우 UPDATE_FAILED 응답
     *         - 변경 성공 후 재 조회에 실패할 경우 UPDATE_FAILED 응답
     */
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new CustomException(ExceptionCode.INVALID_PASSWORD);
        }

        LocalDate beforeDate = schedule.getDate();
        String beforeContent = schedule.getContent();

        if (requestDto.getDate() != null) schedule.updateDate(requestDto.getDate());
        if (requestDto.getContent() != null) schedule.updateContent(requestDto.getContent());

        if (beforeDate.equals(schedule.getDate()) && beforeContent.equals(schedule.getContent())) {
            throw new CustomException(ExceptionCode.NO_CHANGES);
        }

        int result = scheduleRepository.updateSchedule(
                id, schedule.getDate(), schedule.getContent()
        );

        if (result == 0) {
            throw new CustomException(ExceptionCode.UPDATE_FAILED);
        }

        return scheduleRepository.findScheduleWithUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.RELOAD_FAILED));
    }

    /**
     * 일정 삭제
     *  - 성공 시 반환 값 없음
     *  - 삭제할 일정이 없을 경우 NOT_FOUND_SCHEDULE 응답
     *  - 비밀번호가 일치하지 않을 경우 INVALID_PASSWORD 응답
     *  - 변경된 row가 없을 경우 DELETE_FAILED
     *
     * @param id 일정 식별자
     * @param requestDto 일정 비밀번호
     */
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findScheduleEntityById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new CustomException(ExceptionCode.INVALID_PASSWORD);
        }

        int result = scheduleRepository.deleteSchedule(id);

        if (result == 0) {
            throw new CustomException(ExceptionCode.DELETE_FAILED);
        }
    }
}
