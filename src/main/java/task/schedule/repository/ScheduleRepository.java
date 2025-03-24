package task.schedule.repository;

import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.ScheduleLv3;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleLv3 saveSchedule(ScheduleLv3 schedule);
    List<ScheduleResponseDto> findSchedulesWithUserByUserId(Long userId, String updatedDate, Integer limit, Integer offset);
    Optional<ScheduleResponseDto> findScheduleWithUserById(Long id);
    int updateSchedule(Long id, LocalDate date, String content);
    int deleteSchedule(Long id);

    Optional<ScheduleLv3> findScheduleEntityById(Long id);
}
