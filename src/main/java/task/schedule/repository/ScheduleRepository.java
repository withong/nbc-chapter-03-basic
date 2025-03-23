package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.ScheduleLv3;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleLv3 saveSchedule(ScheduleLv3 schedule);
    List<ScheduleLv3> findSchedulesByUserId(Long userId, String authorName, String updatedDate);
    Optional<ScheduleLv3> findScheduleById(Long id);
    int updateSchedule(Long id, String authorName, LocalDate date, String content);
    int deleteSchedule(Long id);
}
