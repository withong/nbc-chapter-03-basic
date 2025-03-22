package task.schedule.service;

import org.springframework.stereotype.Service;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;
import task.schedule.repository.ScheduleRepository;

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

        Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    @Override
    public ScheduleResponseDto findAllSchedule() {
        return null;
    }
}
