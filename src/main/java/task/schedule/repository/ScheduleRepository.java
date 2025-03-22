package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);
}
