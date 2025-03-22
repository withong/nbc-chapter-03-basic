package task.schedule.service;

import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findSchedules(Long userId, String updatedDate);
    ScheduleResponseDto findScheduleById();
}
