package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findSchedules(Long userId, String updatedDate);
}
