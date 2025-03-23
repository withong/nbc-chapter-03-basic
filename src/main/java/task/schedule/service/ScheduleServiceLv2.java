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
public class ScheduleServiceLv2 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceLv2(@Qualifier("scheduleRepositoryLv2") ScheduleRepository scheduleRepository) {
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        if (requestDto.getAuthorName() != null) schedule.updateAuthorName(requestDto.getAuthorName());
        if (requestDto.getDate() != null) schedule.updateDate(requestDto.getDate());
        if (requestDto.getContent() != null) schedule.updateContent(requestDto.getContent());

        int result = scheduleRepository.updateSchedule(
                id, schedule.getAuthorName(), schedule.getDate(), schedule.getContent()
        );

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 변경에 실패했습니다.");
        }

        Schedule updated = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터를 불러오는 데 실패했습니다."));

        return new ScheduleResponseDto(updated);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        int result = scheduleRepository.deleteSchedule(id);

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 삭제에 실패했습니다.");
        }
    }
}
