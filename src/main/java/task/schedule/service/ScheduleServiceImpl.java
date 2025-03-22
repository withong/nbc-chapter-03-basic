package task.schedule.service;

import org.springframework.stereotype.Service;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;
import task.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
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
    public ScheduleResponseDto findScheduleById() {
        return null;
    }
}
