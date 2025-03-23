package task.schedule.service;

import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> findSchedulesByCondition(Long userId, String authorName, String updatedDate);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);
    void deleteSchedule(Long id, ScheduleRequestDto requestDto);
}
