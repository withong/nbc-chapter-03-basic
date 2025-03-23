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
@RequestMapping("/lv3/schedules")
public class ScheduleControllerLv3 {

    private final ScheduleService scheduleService;

    public ScheduleControllerLv3(@Qualifier("scheduleServiceLv3") ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesByUserId(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "updatedDate", required = false) String updatedDate
    ) {
        return new ResponseEntity<>(scheduleService.findSchedulesByUserId(userId, updatedDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleWithUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleWithUserById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("id") Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto before = scheduleService.findScheduleWithUserById(id);
        ScheduleResponseDto after = scheduleService.updateSchedule(id, requestDto);

        boolean isSame = before.getUserName().equals(after.getUserName())
                && before.getDate().equals(after.getDate())
                && before.getContent().equals(after.getContent());

        if (isSame) {
            return new ResponseEntity<>(after, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(after, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @PathVariable("id") Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        scheduleService.deleteSchedule(id, requestDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
