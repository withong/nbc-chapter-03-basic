/*
package task.schedule.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;
import task.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleServiceLv1 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceLv1(@Qualifier("scheduleRepositoryLv1") ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto.getUserId(), requestDto.getAuthorName(),
                requestDto.getDate(), requestDto.getContent(), requestDto.getPassword());

        return new ScheduleResponseDto(scheduleRepository.saveSchedule(schedule));
    }

    @Override
    public List<ScheduleResponseDto> findSchedules(String authorName, String updatedDate) {
        List<Schedule> schedules = scheduleRepository.findSchedules(authorName, updatedDate);
        List<ScheduleResponseDto> responseList = schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return responseList;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));

        return new ScheduleResponseDto(schedule);
    }
}
*/
