package task.schedule.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.schedule.dto.PageResponseDto;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.entity.ScheduleLv3;
import task.schedule.exception.CustomException;
import task.schedule.exception.ExceptionCode;
import task.schedule.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceLv5 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public ScheduleServiceLv5(
            @Qualifier("scheduleRepositoryLv4") ScheduleRepository scheduleRepository,
            UserService userService
    ) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.findUserById(requestDto.getUserId());

        ScheduleLv3 schedule = new ScheduleLv3(
                userResponseDto.getId(), // 사용자 ID 참조
                requestDto.getDate(),
                requestDto.getContent(),
                requestDto.getPassword()
        );

        ScheduleLv3 saved = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(saved, userResponseDto.getName());
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesWithUserByUserId(
            Long userId, String updatedDate, Integer page, Integer size
    ) {
        UserResponseDto userResponseDto = userService.findUserById(userId);

        int offset = (page-1) * size;

        PageResponseDto pageResponseDto = new PageResponseDto(page, size, offset);

        return scheduleRepository.findSchedulesWithUserByUserId(
                userResponseDto.getId(),
                updatedDate,
                pageResponseDto.getSize(),
                pageResponseDto.getOffset()
        );
    }

    @Override
    public ScheduleResponseDto findScheduleWithUserById(Long id) {
        return scheduleRepository.findScheduleWithUserById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleEntityById(id)
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

    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleEntityById(id)
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
