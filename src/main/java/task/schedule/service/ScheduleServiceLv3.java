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
import task.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleServiceLv3 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public ScheduleServiceLv3(
            @Qualifier("scheduleRepositoryLv3") ScheduleRepository scheduleRepository,
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

        if (page == null || page == 0) page = 1;
        if (size == null) size = 5;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleEntityById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        if (requestDto.getDate() != null) schedule.updateDate(requestDto.getDate());
        if (requestDto.getContent() != null) schedule.updateContent(requestDto.getContent());

        int result = scheduleRepository.updateSchedule(
                id, schedule.getDate(), schedule.getContent()
        );

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 변경에 실패했습니다.");
        }

        return scheduleRepository.findScheduleWithUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "데이터를 불러오는 데 실패했습니다."));
    }

    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleEntityById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        int result = scheduleRepository.deleteSchedule(id);

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 삭제에 실패했습니다.");
        }
    }
}
