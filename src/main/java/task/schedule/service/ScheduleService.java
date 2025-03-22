package task.schedule.service;

import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    ScheduleResponseDto findAllSchedule();
}
