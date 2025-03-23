package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findSchedulesByCondition(Long userId, String authorName, String updatedDate);
    Optional<Schedule> findScheduleById(Long id);
    int updateSchedule(Long id, String authorName, LocalDate date, String content);
    int deleteSchedule(Long id);
}
