package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findSchedules(String authorName, String updatedDate);
    Optional<Schedule> findScheduleById(Long id);
}
