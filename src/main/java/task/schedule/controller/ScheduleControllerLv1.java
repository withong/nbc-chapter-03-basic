package task.schedule.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/lv1/schedules")
public class ScheduleControllerLv1 {

    private final ScheduleService scheduleService;

    public ScheduleControllerLv1(@Qualifier("scheduleServiceLv1") ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedules(
            @RequestParam("authorName") String authorName,
            @RequestParam(value = "updatedDate", required = false) String updatedDate
    ) {
        return new ResponseEntity<>(scheduleService.findSchedules(authorName, updatedDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }
}
