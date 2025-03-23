package task.schedule.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.ScheduleLv3;
import task.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
public class ScheduleServiceLv3 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceLv3(@Qualifier("scheduleRepositoryLv3") ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = new ScheduleLv3(requestDto.getUserId(), requestDto.getUserName(),
                requestDto.getDate(), requestDto.getContent(), requestDto.getPassword());

        return new ScheduleResponseDto(scheduleRepository.saveSchedule(schedule));
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByCondition(Long userId, String authorName, String updatedDate) {
/*
        List<Schedule> schedules = scheduleRepository.findSchedulesByCondition(authorName, updatedDate);
        List<ScheduleResponseDto> responseList = schedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();

        return responseList;
*/
        return null;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        if (requestDto.getUserName() != null) schedule.updateAuthorName(requestDto.getUserName());
        if (requestDto.getDate() != null) schedule.updateDate(requestDto.getDate());
        if (requestDto.getContent() != null) schedule.updateContent(requestDto.getContent());

        int result = scheduleRepository.updateSchedule(
                id, schedule.getUserName(), schedule.getDate(), schedule.getContent()
        );

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 변경에 실패했습니다.");
        }

        ScheduleLv3 updated = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터를 불러오는 데 실패했습니다."));

        return new ScheduleResponseDto(updated);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleLv3 schedule = scheduleRepository.findScheduleById(id)
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
