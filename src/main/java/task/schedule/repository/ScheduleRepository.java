package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedulesWithUserByUserId(
            Long userId, LocalDateTime start, LocalDateTime end, Integer limit, Integer offset
    );

    Optional<ScheduleResponseDto> findScheduleWithUserById(Long id);

    int updateSchedule(Long id, LocalDate date, String content);

    int deleteSchedule(Long id);

    void deleteSchedulesByUserId(Long userId);

    Optional<Schedule> findScheduleEntityById(Long id);
}
