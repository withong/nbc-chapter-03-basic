package task.schedule.service;

import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findSchedulesWithUserByUserId(Long userId, LocalDate updatedDate, Integer page, Integer size);
    ScheduleResponseDto findScheduleWithUserById(Long id);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);
    void deleteSchedule(Long id, ScheduleRequestDto requestDto);
}
